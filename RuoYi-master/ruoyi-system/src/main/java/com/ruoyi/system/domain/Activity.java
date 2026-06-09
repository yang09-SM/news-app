package com.ruoyi.system.domain;

import java.math.BigDecimal;
import java.util.Date;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.core.domain.BaseEntity;

public class Activity extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    private Long activityId;

    private String activityName;

    private String activityCode;

    private String coverImage;

    private String description;

    private Date startTime;

    private Date endTime;

    private String activityType;

    private Integer pointsReward;

    private BigDecimal cashReward;

    private Integer maxParticipants;

    private Integer participantCount;

    private String status;

    /** 删除标志（0代表存在 2代表删除） */
    private String delFlag;

    public Long getActivityId()
    {
        return activityId;
    }

    public void setActivityId(Long activityId)
    {
        this.activityId = activityId;
    }

    public String getActivityName()
    {
        return activityName;
    }

    public void setActivityName(String activityName)
    {
        this.activityName = activityName;
    }

    public String getActivityCode()
    {
        return activityCode;
    }

    public void setActivityCode(String activityCode)
    {
        this.activityCode = activityCode;
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

    public Date getStartTime()
    {
        return startTime;
    }

    public void setStartTime(Date startTime)
    {
        this.startTime = startTime;
    }

    public Date getEndTime()
    {
        return endTime;
    }

    public void setEndTime(Date endTime)
    {
        this.endTime = endTime;
    }

    public String getActivityType()
    {
        return activityType;
    }

    public void setActivityType(String activityType)
    {
        this.activityType = activityType;
    }

    public Integer getPointsReward()
    {
        return pointsReward;
    }

    public void setPointsReward(Integer pointsReward)
    {
        this.pointsReward = pointsReward;
    }

    public BigDecimal getCashReward()
    {
        return cashReward;
    }

    public void setCashReward(BigDecimal cashReward)
    {
        this.cashReward = cashReward;
    }

    public Integer getMaxParticipants()
    {
        return maxParticipants;
    }

    public void setMaxParticipants(Integer maxParticipants)
    {
        this.maxParticipants = maxParticipants;
    }

    public Integer getParticipantCount()
    {
        return participantCount;
    }

    public void setParticipantCount(Integer participantCount)
    {
        this.participantCount = participantCount;
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
            .append("activityId", getActivityId())
            .append("activityName", getActivityName())
            .append("activityCode", getActivityCode())
            .append("coverImage", getCoverImage())
            .append("description", getDescription())
            .append("startTime", getStartTime())
            .append("endTime", getEndTime())
            .append("activityType", getActivityType())
            .append("pointsReward", getPointsReward())
            .append("cashReward", getCashReward())
            .append("maxParticipants", getMaxParticipants())
            .append("participantCount", getParticipantCount())
            .append("status", getStatus())
            .append("delFlag", getDelFlag())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }
}
