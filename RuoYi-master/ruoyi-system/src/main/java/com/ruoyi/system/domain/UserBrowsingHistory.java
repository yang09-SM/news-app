package com.ruoyi.system.domain;

import java.util.Date;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.core.domain.BaseEntity;

public class UserBrowsingHistory extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    private Long historyId;

    private Long userId;

    private Long articleId;

    private String articleTitle;

    private String articleCover;

    private Date browseTime;

    /** 删除标志（0代表存在 2代表删除） */
    private String delFlag;

    public Long getHistoryId()
    {
        return historyId;
    }

    public void setHistoryId(Long historyId)
    {
        this.historyId = historyId;
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

    public Date getBrowseTime()
    {
        return browseTime;
    }

    public void setBrowseTime(Date browseTime)
    {
        this.browseTime = browseTime;
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
            .append("historyId", getHistoryId())
            .append("userId", getUserId())
            .append("articleId", getArticleId())
            .append("articleTitle", getArticleTitle())
            .append("articleCover", getArticleCover())
            .append("browseTime", getBrowseTime())
            .append("delFlag", getDelFlag())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }
}
