package com.ruoyi.system.domain.es;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Date;

/**
 * 新闻文章ES文档
 * 映射Elasticsearch索引 news_article
 */
public class NewsArticleDocument
{
    @JsonProperty("article_id")
    private Long articleId;

    /**
     * 标题 - 使用ik_max_word分词器进行索引，ik_smart分词器进行搜索
     */
    @JsonProperty("title")
    private String title;

    /**
     * 摘要 - 使用ik_max_word分词器进行索引，ik_smart分词器进行搜索
     */
    @JsonProperty("summary")
    private String summary;

    /**
     * 内容 - 使用ik_max_word分词器进行索引，ik_smart分词器进行搜索
     */
    @JsonProperty("content")
    private String content;

    /**
     * 状态 - 精确匹配
     */
    @JsonProperty("status")
    private String status;

    /**
     * 分类ID
     */
    @JsonProperty("category_id")
    private Long categoryId;

    /**
     * 浏览次数
     */
    @JsonProperty("view_count")
    private Long viewCount;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @JsonProperty("create_time")
    private Date createTime;

    /**
     * 新闻类型
     */
    @JsonProperty("news_type")
    private String newsType;

    /**
     * 来源
     */
    @JsonProperty("source")
    private String source;

    /**
     * 作者ID
     */
    @JsonProperty("author_id")
    private Long authorId;

    // Getters and Setters

    public Long getArticleId()
    {
        return articleId;
    }

    public void setArticleId(Long articleId)
    {
        this.articleId = articleId;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public String getSummary()
    {
        return summary;
    }

    public void setSummary(String summary)
    {
        this.summary = summary;
    }

    public String getContent()
    {
        return content;
    }

    public void setContent(String content)
    {
        this.content = content;
    }

    public String getStatus()
    {
        return status;
    }

    public void setStatus(String status)
    {
        this.status = status;
    }

    public Long getCategoryId()
    {
        return categoryId;
    }

    public void setCategoryId(Long categoryId)
    {
        this.categoryId = categoryId;
    }

    public Long getViewCount()
    {
        return viewCount;
    }

    public void setViewCount(Long viewCount)
    {
        this.viewCount = viewCount;
    }

    public Date getCreateTime()
    {
        return createTime;
    }

    public void setCreateTime(Date createTime)
    {
        this.createTime = createTime;
    }

    public String getNewsType()
    {
        return newsType;
    }

    public void setNewsType(String newsType)
    {
        this.newsType = newsType;
    }

    public String getSource()
    {
        return source;
    }

    public void setSource(String source)
    {
        this.source = source;
    }

    public Long getAuthorId()
    {
        return authorId;
    }

    public void setAuthorId(Long authorId)
    {
        this.authorId = authorId;
    }
}
