package com.ruoyi.system.service;

import com.ruoyi.system.domain.NewsArticle;
import java.util.List;
import java.util.Map;

/**
 * 新闻搜索服务接口（基于Elasticsearch）
 */
public interface INewsSearchService
{
    /**
     * 搜索新闻文章
     *
     * @param keyword 搜索关键词
     * @param page 页码（从1开始）
     * @param size 每页大小
     * @return 搜索结果（包含高亮信息）
     */
    Map<String, Object> search(String keyword, int page, int size);

    /**
     * 搜索联想建议
     *
     * @param keyword 前缀关键词
     * @param count 返回数量
     * @return 联想建议列表
     */
    List<String> suggest(String keyword, int count);

    /**
     * 获取搜索热词排行
     *
     * @param count 返回数量
     * @return 热词列表（按热度排序）
     */
    List<Map<String, Object>> getHotKeywords(int count);

    /**
     * 同步单篇文章到ES索引
     *
     * @param article 新闻文章
     */
    void indexArticle(NewsArticle article);

    /**
     * 从ES删除文章
     *
     * @param articleId 文章ID
     */
    void deleteArticle(Long articleId);

    /**
     * 批量索引文章到ES
     *
     * @param articles 文章列表
     */
    void batchIndex(List<NewsArticle> articles);

    /**
     * 检查ES是否可用
     *
     * @return true-可用，false-不可用
     */
    boolean isAvailable();
}
