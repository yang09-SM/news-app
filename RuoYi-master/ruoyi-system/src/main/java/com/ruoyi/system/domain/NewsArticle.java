package com.ruoyi.system.domain;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.core.domain.BaseEntity;
import com.ruoyi.common.xss.Xss;

public class NewsArticle extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    private Long articleId;

    private String title;

    private Long categoryId;

    private String coverImage;

    private String summary;

    private String content;

    private String status;

    private Long viewCount;

    private Long authorId;

    private String newsType;

    private String videoUrl;

    private String audioUrl;

    private String duration;

    private String source;

    private Long likeCount;

    private Long commentCount;

    /** 删除标志（0代表存在 2代表删除） */
    private String delFlag;

    public Long getArticleId()
    {
        return articleId;
    }

    public void setArticleId(Long articleId)
    {
        this.articleId = articleId;
    }

    @Xss(message = "新闻标题不能包含脚本字符")
    @NotBlank(message = "新闻标题不能为空")
    @Size(min = 0, max = 200, message = "新闻标题不能超过200个字符")
    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public Long getCategoryId()
    {
        return categoryId;
    }

    public void setCategoryId(Long categoryId)
    {
        this.categoryId = categoryId;
    }

    public String getCoverImage()
    {
        return coverImage;
    }

    public void setCoverImage(String coverImage)
    {
        this.coverImage = coverImage;
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

    public Long getViewCount()
    {
        return viewCount;
    }

    public void setViewCount(Long viewCount)
    {
        this.viewCount = viewCount;
    }

    public Long getAuthorId()
    {
        return authorId;
    }

    public void setAuthorId(Long authorId)
    {
        this.authorId = authorId;
    }

    public String getNewsType()
    {
        return newsType;
    }

    public void setNewsType(String newsType)
    {
        this.newsType = newsType;
    }

    public String getVideoUrl()
    {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl)
    {
        this.videoUrl = videoUrl;
    }

    public String getAudioUrl()
    {
        return audioUrl;
    }

    public void setAudioUrl(String audioUrl)
    {
        this.audioUrl = audioUrl;
    }

    public String getDuration()
    {
        return duration;
    }

    public void setDuration(String duration)
    {
        this.duration = duration;
    }

    public String getSource()
    {
        return source;
    }

    public void setSource(String source)
    {
        this.source = source;
    }

    public Long getLikeCount()
    {
        return likeCount;
    }

    public void setLikeCount(Long likeCount)
    {
        this.likeCount = likeCount;
    }

    public Long getCommentCount()
    {
        return commentCount;
    }

    public void setCommentCount(Long commentCount)
    {
        this.commentCount = commentCount;
    }

    public String getDelFlag()
    {
        return delFlag;
    }

    public void setDelFlag(String delFlag)
    {
        this.delFlag = delFlag;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("articleId", getArticleId())
            .append("title", getTitle())
            .append("categoryId", getCategoryId())
            .append("coverImage", getCoverImage())
            .append("summary", getSummary())
            .append("content", getContent())
            .append("status", getStatus())
            .append("viewCount", getViewCount())
            .append("authorId", getAuthorId())
            .append("newsType", getNewsType())
            .append("videoUrl", getVideoUrl())
            .append("audioUrl", getAudioUrl())
            .append("duration", getDuration())
            .append("source", getSource())
            .append("likeCount", getLikeCount())
            .append("commentCount", getCommentCount())
            .append("delFlag", getDelFlag())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }
}
