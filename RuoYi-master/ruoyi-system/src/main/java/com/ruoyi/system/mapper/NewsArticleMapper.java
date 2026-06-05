package com.ruoyi.system.mapper;

import java.util.List;
import com.ruoyi.system.domain.NewsArticle;

public interface NewsArticleMapper
{
    public NewsArticle selectNewsArticleById(Long articleId);

    public List<NewsArticle> selectNewsArticleList(NewsArticle newsArticle);

    public int insertNewsArticle(NewsArticle newsArticle);

    public int updateNewsArticle(NewsArticle newsArticle);

    public int deleteNewsArticleByIds(String[] articleIds);

    public List<NewsArticle> selectPublishedNewsList(NewsArticle newsArticle);

    public int incrementViewCount(Long articleId);
}
