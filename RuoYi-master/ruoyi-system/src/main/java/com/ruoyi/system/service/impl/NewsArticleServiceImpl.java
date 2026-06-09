package com.ruoyi.system.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.common.core.text.Convert;
import com.ruoyi.system.domain.NewsArticle;
import com.ruoyi.system.mapper.NewsArticleMapper;
import com.ruoyi.system.service.INewsArticleService;

@Service
public class NewsArticleServiceImpl implements INewsArticleService
{
    @Autowired
    private NewsArticleMapper newsArticleMapper;

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
        return newsArticleMapper.insertNewsArticle(newsArticle);
    }

    @Override
    public int updateNewsArticle(NewsArticle newsArticle)
    {
        return newsArticleMapper.updateNewsArticle(newsArticle);
    }

    @Override
    public int deleteNewsArticleByIds(String ids)
    {
        return newsArticleMapper.deleteNewsArticleByIds(Convert.toStrArray(ids));
    }

    @Override
    public List<NewsArticle> selectPublishedNewsList(NewsArticle newsArticle)
    {
        return newsArticleMapper.selectPublishedNewsList(newsArticle);
    }

    @Override
    public NewsArticle selectNewsArticleDetail(Long articleId)
    {
        newsArticleMapper.incrementViewCount(articleId);
        return newsArticleMapper.selectNewsArticleById(articleId);
    }

    @Override
    public int incrementViewCount(Long articleId)
    {
        return newsArticleMapper.incrementViewCount(articleId);
    }

    @Override
    public List<NewsArticle> selectHotNewsList()
    {
        return newsArticleMapper.selectHotNewsList();
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
