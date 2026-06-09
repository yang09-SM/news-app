package com.ruoyi.system.domain;

import java.util.Date;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.core.domain.BaseEntity;

public class UserDislikedNews extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    private Long dislikeId;

    private Long userId;

    private Long articleId;

    private Date dislikeTime;

    /** 删除标志（0代表存在 2代表删除） */
    private String delFlag;

    public Long getDislikeId()
    {
        return dislikeId;
    }

    public void setDislikeId(Long dislikeId)
    {
        this.dislikeId = dislikeId;
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

    public Date getDislikeTime()
    {
        return dislikeTime;
    }

    public void setDislikeTime(Date dislikeTime)
    {
        this.dislikeTime = dislikeTime;
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
            .append("dislikeId", getDislikeId())
            .append("userId", getUserId())
            .append("articleId", getArticleId())
            .append("dislikeTime", getDislikeTime())
            .append("delFlag", getDelFlag())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }
}
