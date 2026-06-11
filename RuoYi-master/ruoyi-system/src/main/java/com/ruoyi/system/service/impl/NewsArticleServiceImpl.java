package com.ruoyi.system.service.impl;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.common.core.push.JPushService;
import com.ruoyi.common.core.redis.RedisCacheService;
import com.ruoyi.common.core.text.Convert;
import com.ruoyi.system.domain.NewsArticle;
import com.ruoyi.system.mapper.NewsArticleMapper;
import com.ruoyi.system.service.INewsArticleService;
import com.ruoyi.system.service.INewsSearchService;

@Service
public class NewsArticleServiceImpl implements INewsArticleService
{
    private static final Logger log = LoggerFactory.getLogger(NewsArticleServiceImpl.class);

    @Autowired
    private NewsArticleMapper newsArticleMapper;

    @Autowired
    private INewsSearchService newsSearchService;

    @Autowired
    private RedisCacheService redisCacheService;

    @Autowired
    private JPushService jPushService;

    /** 热门列表缓存Key */
    private static final String CACHE_HOT_LIST = "hot:list";
    /** 文章详情缓存Key前缀 */
    private static final String CACHE_ARTICLE_DETAIL_PREFIX = "article:detail:";
    /** 分类列表缓存Key前缀 */
    private static final String CACHE_CATEGORY_PREFIX = "category:list:";

    @Override
    public NewsArticle selectNewsArticleById(Long articleId)
    {
        return newsArticleMapper.selectNewsArticleById(articleId);
    }

    @Override
    public List<NewsArticle> selectNewsArticleList(NewsArticle newsArticle)
    {
        return newsArticleMapper.selectNewsArticleList(newsArticle);
    }

    @Override
    public int insertNewsArticle(NewsArticle newsArticle)
    {
        int rows = newsArticleMapper.insertNewsArticle(newsArticle);
        if (rows > 0)
        {
            // 清除热门列表缓存和分类列表缓存
            redisCacheService.delete(CACHE_HOT_LIST);
            if (newsArticle.getCategoryId() != null)
            {
                redisCacheService.delete(CACHE_CATEGORY_PREFIX + newsArticle.getCategoryId());
            }

            // 同步到Elasticsearch
            newsSearchService.indexArticle(newsArticle);
        }
        return rows;
    }

    @Override
    public int updateNewsArticle(NewsArticle newsArticle)
    {
        // 获取更新前的文章状态（用于判断是否从草稿变为发布）
        NewsArticle oldArticle = null;
        if (newsArticle.getArticleId() != null)
        {
            oldArticle = newsArticleMapper.selectNewsArticleById(newsArticle.getArticleId());
        }

        int rows = newsArticleMapper.updateNewsArticle(newsArticle);

        // 重新同步到Elasticsearch
        if (rows > 0)
        {
            newsSearchService.indexArticle(newsArticle);
            // 清除相关缓存
            redisCacheService.delete(CACHE_ARTICLE_DETAIL_PREFIX + newsArticle.getArticleId());
            redisCacheService.delete(CACHE_HOT_LIST);
            if (newsArticle.getCategoryId() != null)
            {
                redisCacheService.delete(CACHE_CATEGORY_PREFIX + newsArticle.getCategoryId());
            }

            // 当文章状态从0（草稿）变为1（发布）时，发送推送通知
            if (jPushService.isPushEnabled() && oldArticle != null
                    && "0".equals(oldArticle.getStatus()) && "1".equals(newsArticle.getStatus()))
            {
                try
                {
                    // 使用分类ID作为标签进行推送
                    String categoryId = newsArticle.getCategoryId() != null ? newsArticle.getCategoryId().toString() : "all";
                    jPushService.pushByTag(
                        categoryId,
                        newsArticle.getTitle(),
                        newsArticle.getSummary() != null ? newsArticle.getSummary() : newsArticle.getTitle()
                    );
                    log.info("新闻发布推送成功: articleId={}, title={}", newsArticle.getArticleId(), newsArticle.getTitle());
                }
                catch (Exception e)
                {
                    log.error("新闻发布推送失败: articleId={}, title={}", newsArticle.getArticleId(), newsArticle.getTitle(), e);
                }
            }
        }

        return rows;
    }

    @Override
    public int deleteNewsArticleByIds(String ids)
    {
        int rows = newsArticleMapper.deleteNewsArticleByIds(Convert.toStrArray(ids));
        if (rows > 0)
        {
            // 批量删除相关文章的详情缓存，清除热门列表缓存
            redisCacheService.deletePattern(CACHE_ARTICLE_DETAIL_PREFIX + "*");
            redisCacheService.delete(CACHE_HOT_LIST);
            // 清除所有分类列表缓存
            redisCacheService.deletePattern(CACHE_CATEGORY_PREFIX + "*");

            // 从Elasticsearch删除文章
            String[] idArray = Convert.toStrArray(ids);
            for (String id : idArray)
            {
                newsSearchService.deleteArticle(Long.valueOf(id));
            }
        }
        return rows;
    }

    @Override
    public List<NewsArticle> selectPublishedNewsList(NewsArticle newsArticle)
    {
        return newsArticleMapper.selectPublishedNewsList(newsArticle);
    }

    @Override
    public NewsArticle selectNewsArticleDetail(Long articleId)
    {
        // 先查Redis缓存
        String cacheKey = CACHE_ARTICLE_DETAIL_PREFIX + articleId;
        try
        {
            NewsArticle cached = redisCacheService.get(cacheKey, NewsArticle.class);
            if (cached != null)
            {
                // 缓存命中，直接返回（不递增浏览量）
                return cached;
            }
        }
        catch (Exception e)
        {
            // Redis异常降级为直连DB
        }

        // 缓存未命中，查DB并递增浏览量
        newsArticleMapper.incrementViewCount(articleId);
        NewsArticle article = newsArticleMapper.selectNewsArticleById(articleId);

        // 写入Redis缓存（TTL=10分钟）
        if (article != null)
        {
            redisCacheService.set(cacheKey, article, 600);
        }

        return article;
    }

    @Override
    public int incrementViewCount(Long articleId)
    {
        return newsArticleMapper.incrementViewCount(articleId);
    }

    @Override
    public List<NewsArticle> selectHotNewsList()
    {
        // 先查Redis缓存
        try
        {
            List<NewsArticle> cached = redisCacheService.get(CACHE_HOT_LIST, List.class);
            if (cached != null && !cached.isEmpty())
            {
                return cached;
            }
        }
        catch (Exception e)
        {
            // Redis异常降级为直连DB
        }

        // 缓存未命中，查DB
        List<NewsArticle> list = newsArticleMapper.selectHotNewsList();

        // 写入Redis缓存（TTL=5分钟）
        if (list != null && !list.isEmpty())
        {
            redisCacheService.set(CACHE_HOT_LIST, list, 300);
        }

        return list;
    }

    @Override
    public List<NewsArticle> searchNews(String keyword)
    {
        return newsArticleMapper.searchNews(keyword);
    }

    @Override
    public List<NewsArticle> selectRecommendedNews(Long userId)
    {
        return newsArticleMapper.selectRecommendedNews(userId);
    }

    @Override
    public List<NewsArticle> selectNewsByCategory(Long categoryId)
    {
        return newsArticleMapper.selectNewsByCategory(categoryId);
    }
}
