package com.ruoyi.system.domain;

import java.util.Date;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.core.domain.BaseEntity;

public class ChannelSubscription extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    private Long subscriptionId;

    private Long userId;

    private Long channelId;

    private Date subscribeTime;

    /** 删除标志（0代表存在 2代表删除） */
    private String delFlag;

    public Long getSubscriptionId()
    {
        return subscriptionId;
    }

    public void setSubscriptionId(Long subscriptionId)
    {
        this.subscriptionId = subscriptionId;
    }

    public Long getUserId()
    {
        return userId;
    }

    public void setUserId(Long userId)
    {
        this.userId = userId;
    }

    public Long getChannelId()
    {
        return channelId;
    }

    public void setChannelId(Long channelId)
    {
        this.channelId = channelId;
    }

    public Date getSubscribeTime()
    {
        return subscribeTime;
    }

    public void setSubscribeTime(Date subscribeTime)
    {
        this.subscribeTime = subscribeTime;
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
            .append("subscriptionId", getSubscriptionId())
            .append("userId", getUserId())
            .append("channelId", getChannelId())
            .append("subscribeTime", getSubscribeTime())
            .append("delFlag", getDelFlag())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }
}
