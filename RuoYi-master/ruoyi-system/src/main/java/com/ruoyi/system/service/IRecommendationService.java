package com.ruoyi.system.service;

import java.util.List;
import com.ruoyi.system.domain.NewsArticle;

/**
 * 推荐系统服务接口
 */
public interface IRecommendationService
{
    /**
     * 基于用户的协同过滤推荐
     * 找到与目标用户兴趣相似的用户，推荐他们喜欢但目标用户未看过的新闻
     */
    List<NewsArticle> recommendByUserCollaborative(Long userId, int limit);

    /**
     * 基于内容的推荐
     * 根据用户浏览历史中的新闻分类，推荐同分类的新闻
     */
    List<NewsArticle> recommendByContent(Long userId, int limit);

    /**
     * 热门推荐（冷启动方案）
     * 按浏览量排序推荐热门新闻
     */
    List<NewsArticle> recommendByHot(int limit);

    /**
     * 综合推荐
     * 融合协同过滤、内容推荐和热门推荐的结果
     */
    List<NewsArticle> recommendCombined(Long userId, int limit);
}
