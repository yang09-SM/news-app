package com.ruoyi.system.service;

import java.util.List;
import com.ruoyi.system.domain.NewsArticle;

public interface INewsArticleService
{
    public NewsArticle selectNewsArticleById(Long articleId);

    public List<NewsArticle> selectNewsArticleList(NewsArticle newsArticle);

    public int insertNewsArticle(NewsArticle newsArticle);

    public int updateNewsArticle(NewsArticle newsArticle);

    public int deleteNewsArticleByIds(String ids);

    public List<NewsArticle> selectPublishedNewsList(NewsArticle newsArticle);

    public NewsArticle selectNewsArticleDetail(Long articleId);

    public int incrementViewCount(Long articleId);
}
