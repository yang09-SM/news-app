package com.ruoyi.system.domain;

import java.util.Date;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.core.domain.BaseEntity;

public class UserFavorite extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    private Long favoriteId;

    private Long userId;

    private Long articleId;

    private String articleTitle;

    private String articleCover;

    private Date favoriteTime;

    /** 删除标志（0代表存在 2代表删除） */
    private String delFlag;

    public Long getFavoriteId()
    {
        return favoriteId;
    }

    public void setFavoriteId(Long favoriteId)
    {
        this.favoriteId = favoriteId;
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

    public Date getFavoriteTime()
    {
        return favoriteTime;
    }

    public void setFavoriteTime(Date favoriteTime)
    {
        this.favoriteTime = favoriteTime;
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
            .append("favoriteId", getFavoriteId())
            .append("userId", getUserId())
            .append("articleId", getArticleId())
            .append("articleTitle", getArticleTitle())
            .append("articleCover", getArticleCover())
            .append("favoriteTime", getFavoriteTime())
            .append("delFlag", getDelFlag())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }
}
