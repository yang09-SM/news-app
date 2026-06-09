package com.ruoyi.system.domain;

import java.util.Date;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.core.domain.BaseEntity;

public class UserFollow extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    private Long followId;

    private Long userId;

    private Long followUserId;

    private Date followTime;

    /** 删除标志（0代表存在 2代表删除） */
    private String delFlag;

    public Long getFollowId()
    {
        return followId;
    }

    public void setFollowId(Long followId)
    {
        this.followId = followId;
    }

    public Long getUserId()
    {
        return userId;
    }

    public void setUserId(Long userId)
    {
        this.userId = userId;
    }

    public Long getFollowUserId()
    {
        return followUserId;
    }

    public void setFollowUserId(Long followUserId)
    {
        this.followUserId = followUserId;
    }

    public Date getFollowTime()
    {
        return followTime;
    }

    public void setFollowTime(Date followTime)
    {
        this.followTime = followTime;
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
            .append("followId", getFollowId())
            .append("userId", getUserId())
            .append("followUserId", getFollowUserId())
            .append("followTime", getFollowTime())
            .append("delFlag", getDelFlag())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }
}
