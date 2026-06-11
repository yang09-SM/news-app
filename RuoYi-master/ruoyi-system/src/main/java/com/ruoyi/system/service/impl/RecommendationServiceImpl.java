package com.ruoyi.system.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.common.core.redis.RedisCacheService;
import com.ruoyi.system.domain.NewsArticle;
import com.ruoyi.system.domain.UserBrowsingHistory;
import com.ruoyi.system.domain.UserFavorite;
import com.ruoyi.system.domain.UserInterest;
import com.ruoyi.system.mapper.NewsArticleMapper;
import com.ruoyi.system.mapper.UserBrowsingHistoryMapper;
import com.ruoyi.system.mapper.UserFavoriteMapper;
import com.ruoyi.system.mapper.UserInterestMapper;
import com.ruoyi.system.service.IRecommendationService;

/**
 * 推荐系统服务实现
 * 实现协同过滤、内容推荐和热门推荐
 */
@Service
public class RecommendationServiceImpl implements IRecommendationService
{
    @Autowired
    private NewsArticleMapper newsArticleMapper;

    @Autowired
    private UserBrowsingHistoryMapper browsingHistoryMapper;

    @Autowired
    private UserFavoriteMapper userFavoriteMapper;

    @Autowired
    private UserInterestMapper userInterestMapper;

    @Autowired
    private RedisCacheService redisCacheService;

    /** 推荐结果缓存Key前缀 */
    private static final String CACHE_RECOMMEND_PREFIX = "recommend:";

    @Override
    public List<NewsArticle> recommendByUserCollaborative(Long userId, int limit)
    {
        // 1. 获取目标用户的浏览历史
        UserBrowsingHistory query = new UserBrowsingHistory();
        query.setUserId(userId);
        List<UserBrowsingHistory> targetHistory = browsingHistoryMapper.selectBrowsingHistoryList(query);

        if (targetHistory.isEmpty())
        {
            return Collections.emptyList();
        }

        Set<Long> targetArticles = targetHistory.stream()
                .map(UserBrowsingHistory::getArticleId)
                .collect(Collectors.toSet());

        // 2. 找到浏览过相同文章的其他用户（相似用户）
        Map<Long, Integer> similarUsers = new HashMap<>();
        for (Long articleId : targetArticles)
        {
            UserBrowsingHistory articleQuery = new UserBrowsingHistory();
            articleQuery.setArticleId(articleId);
            List<UserBrowsingHistory> otherViewers = browsingHistoryMapper.selectBrowsingHistoryList(articleQuery);
            for (UserBrowsingHistory h : otherViewers)
            {
                if (!h.getUserId().equals(userId))
                {
                    similarUsers.merge(h.getUserId(), 1, Integer::sum);
                }
            }
        }

        // 3. 按相似度排序，取前5个相似用户
        List<Long> topSimilarUsers = similarUsers.entrySet().stream()
                .sorted(Map.Entry.<Long, Integer>comparingByValue().reversed())
                .limit(5)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());

        // 4. 收集相似用户浏览过但目标用户未浏览的文章
        Set<Long> recommendedArticleIds = new LinkedHashSet<>();
        for (Long similarUserId : topSimilarUsers)
        {
            UserBrowsingHistory similarQuery = new UserBrowsingHistory();
            similarQuery.setUserId(similarUserId);
            List<UserBrowsingHistory> similarHistory = browsingHistoryMapper.selectBrowsingHistoryList(similarQuery);
            for (UserBrowsingHistory h : similarHistory)
            {
                if (!targetArticles.contains(h.getArticleId()))
                {
                    recommendedArticleIds.add(h.getArticleId());
                }
            }
        }

        // 5. 查询推荐文章详情
        List<NewsArticle> result = new ArrayList<>();
        int count = 0;
        for (Long articleId : recommendedArticleIds)
        {
            if (count >= limit) break;
            NewsArticle article = newsArticleMapper.selectNewsArticleById(articleId);
            if (article != null && "1".equals(article.getStatus()))
            {
                result.add(article);
                count++;
            }
        }

        return result;
    }

    @Override
    public List<NewsArticle> recommendByContent(Long userId, int limit)
    {
        // 1. 获取用户兴趣标签
        UserInterest interestQuery = new UserInterest();
        interestQuery.setUserId(userId);
        List<UserInterest> interests = userInterestMapper.selectInterestList(interestQuery);

        // 2. 获取用户浏览历史中的分类偏好
        UserBrowsingHistory historyQuery = new UserBrowsingHistory();
        historyQuery.setUserId(userId);
        List<UserBrowsingHistory> history = browsingHistoryMapper.selectBrowsingHistoryList(historyQuery);

        // 统计分类偏好
        Map<Long, Integer> categoryPreference = new HashMap<>();
        for (UserBrowsingHistory h : history)
        {
            NewsArticle article = newsArticleMapper.selectNewsArticleById(h.getArticleId());
            if (article != null)
            {
                categoryPreference.merge(article.getCategoryId(), 1, Integer::sum);
            }
        }

        // 3. 获取用户已浏览的文章ID
        Set<Long> viewedArticleIds = history.stream()
                .map(UserBrowsingHistory::getArticleId)
                .collect(Collectors.toSet());

        // 4. 按分类偏好推荐未浏览的新闻
        List<NewsArticle> result = new ArrayList<>();
        List<Map.Entry<Long, Integer>> sortedCategories = categoryPreference.entrySet().stream()
                .sorted(Map.Entry.<Long, Integer>comparingByValue().reversed())
                .collect(Collectors.toList());

        Set<Long> addedIds = new HashSet<>();
        for (Map.Entry<Long, Integer> entry : sortedCategories)
        {
            if (result.size() >= limit) break;
            NewsArticle categoryQuery = new NewsArticle();
            categoryQuery.setCategoryId(entry.getKey());
            categoryQuery.setStatus("1");
            List<NewsArticle> categoryArticles = newsArticleMapper.selectPublishedNewsList(categoryQuery);
            for (NewsArticle article : categoryArticles)
            {
                if (result.size() >= limit) break;
                if (!viewedArticleIds.contains(article.getArticleId()) && !addedIds.contains(article.getArticleId()))
                {
                    result.add(article);
                    addedIds.add(article.getArticleId());
                }
            }
        }

        return result;
    }

    @Override
    public List<NewsArticle> recommendByHot(int limit)
    {
        List<NewsArticle> hotNews = newsArticleMapper.selectHotNewsList();
        if (hotNews.size() <= limit)
        {
            return hotNews;
        }
        return hotNews.subList(0, limit);
    }

    @Override
    public List<NewsArticle> recommendCombined(Long userId, int limit)
    {
        // 构建缓存Key，userId为null时使用"anonymous"
        String cacheKey = CACHE_RECOMMEND_PREFIX + (userId != null ? userId.toString() : "anonymous");

        // 先查Redis缓存
        try
        {
            List<NewsArticle> cached = redisCacheService.get(cacheKey, List.class);
            if (cached != null && !cached.isEmpty())
            {
                return cached;
            }
        }
        catch (Exception e)
        {
            // Redis异常降级为直连DB执行推荐算法
        }

        // 缓存未命中，执行推荐算法
        List<NewsArticle> result = doRecommendCombined(userId, limit);

        // 写入Redis缓存（TTL=30分钟）
        if (result != null && !result.isEmpty())
        {
            redisCacheService.set(cacheKey, result, 1800);
        }

        return result;
    }

    /**
     * 执行组合推荐算法（内部方法，不含缓存逻辑）
     */
    private List<NewsArticle> doRecommendCombined(Long userId, int limit)
    {
        if (userId == null)
        {
            return recommendByHot(limit);
        }

        Set<Long> addedIds = new HashSet<>();
        List<NewsArticle> result = new ArrayList<>();

        // 权重分配：协同过滤40%，内容推荐40%，热门推荐20%
        int cfLimit = (int) Math.ceil(limit * 0.4);
        int contentLimit = (int) Math.ceil(limit * 0.4);
        int hotLimit = limit - cfLimit - contentLimit;
        if (hotLimit < 0) hotLimit = 0;

        // 协同过滤推荐
        List<NewsArticle> cfResults = recommendByUserCollaborative(userId, cfLimit);
        for (NewsArticle article : cfResults)
        {
            if (addedIds.add(article.getArticleId()))
            {
                result.add(article);
            }
        }

        // 内容推荐
        List<NewsArticle> contentResults = recommendByContent(userId, contentLimit);
        for (NewsArticle article : contentResults)
        {
            if (addedIds.add(article.getArticleId()))
            {
                result.add(article);
            }
        }

        // 热门推荐补充
        if (result.size() < limit)
        {
            List<NewsArticle> hotResults = recommendByHot(limit);
            for (NewsArticle article : hotResults)
            {
                if (result.size() >= limit) break;
                if (addedIds.add(article.getArticleId()))
                {
                    result.add(article);
                }
            }
        }

        // 如果协同过滤和内容推荐都没有结果，全部用热门推荐
        if (result.isEmpty())
        {
            return recommendByHot(limit);
        }

        return result;
    }
}
