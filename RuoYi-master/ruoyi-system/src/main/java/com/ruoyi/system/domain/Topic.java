package com.ruoyi.system.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.core.domain.BaseEntity;

public class Topic extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    private Long topicId;

    private String topicName;

    private String topicCode;

    private String coverImage;

    private String description;

    private Long newsCount;

    private Long discussionCount;

    private Integer orderNum;

    private String status;

    /** 删除标志（0代表存在 2代表删除） */
    private String delFlag;

    public Long getTopicId()
    {
        return topicId;
    }

    public void setTopicId(Long topicId)
    {
        this.topicId = topicId;
    }

    public String getTopicName()
    {
        return topicName;
    }

    public void setTopicName(String topicName)
    {
        this.topicName = topicName;
    }

    public String getTopicCode()
    {
        return topicCode;
    }

    public void setTopicCode(String topicCode)
    {
        this.topicCode = topicCode;
    }

    public String getCoverImage()
    {
        return coverImage;
    }

    public void setCoverImage(String coverImage)
    {
        this.coverImage = coverImage;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public Long getNewsCount()
    {
        return newsCount;
    }

    public void setNewsCount(Long newsCount)
    {
        this.newsCount = newsCount;
    }

    public Long getDiscussionCount()
    {
        return discussionCount;
    }

    public void setDiscussionCount(Long discussionCount)
    {
        this.discussionCount = discussionCount;
    }

    public Integer getOrderNum()
    {
        return orderNum;
    }

    public void setOrderNum(Integer orderNum)
    {
        this.orderNum = orderNum;
    }

    public String getStatus()
    {
        return status;
    }

    public void setStatus(String status)
    {
        this.status = status;
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
            .append("topicId", getTopicId())
            .append("topicName", getTopicName())
            .append("topicCode", getTopicCode())
            .append("coverImage", getCoverImage())
            .append("description", getDescription())
            .append("newsCount", getNewsCount())
            .append("discussionCount", getDiscussionCount())
            .append("orderNum", getOrderNum())
            .append("status", getStatus())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }
}
