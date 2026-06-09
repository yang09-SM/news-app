package com.ruoyi.system.domain;

import java.util.Date;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.core.domain.BaseEntity;

public class OfflineNews extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    private Long offlineId;

    private Long userId;

    private Long articleId;

    private String articleTitle;

    private String articleCover;

    private String articleContent;

    private Date downloadTime;

    private Long fileSize;

    /** 删除标志（0代表存在 2代表删除） */
    private String delFlag;

    public Long getOfflineId()
    {
        return offlineId;
    }

    public void setOfflineId(Long offlineId)
    {
        this.offlineId = offlineId;
    }

    public Long getUserId()
    {
        return userId;
    }

    public void setUserId(Long userId)
    {
        this.userId = userId;
    }

    public Long getArticleId()
    {
        return articleId;
    }

    public void setArticleId(Long articleId)
    {
        this.articleId = articleId;
    }

    public String getArticleTitle()
    {
        return articleTitle;
    }

    public void setArticleTitle(String articleTitle)
    {
        this.articleTitle = articleTitle;
    }

    public String getArticleCover()
    {
        return articleCover;
    }

    public void setArticleCover(String articleCover)
    {
        this.articleCover = articleCover;
    }

    public String getArticleContent()
    {
        return articleContent;
    }

    public void setArticleContent(String articleContent)
    {
        this.articleContent = articleContent;
    }

    public Date getDownloadTime()
    {
        return downloadTime;
    }

    public void setDownloadTime(Date downloadTime)
    {
        this.downloadTime = downloadTime;
    }

    public Long getFileSize()
    {
        return fileSize;
    }

    public void setFileSize(Long fileSize)
    {
        this.fileSize = fileSize;
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
            .append("offlineId", getOfflineId())
            .append("userId", getUserId())
            .append("articleId", getArticleId())
            .append("articleTitle", getArticleTitle())
            .append("articleCover", getArticleCover())
            .append("articleContent", getArticleContent())
            .append("downloadTime", getDownloadTime())
            .append("fileSize", getFileSize())
            .append("delFlag", getDelFlag())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }
}
