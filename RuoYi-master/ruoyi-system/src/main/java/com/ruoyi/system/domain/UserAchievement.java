package com.ruoyi.system.domain;

import java.util.Date;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.core.domain.BaseEntity;

public class UserAchievement extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    private Long achievementId;

    private Long userId;

    private String achievementName;

    private String achievementCode;

    private String description;

    private String icon;

    private Integer pointsReward;

    private String isUnlocked;

    private Date unlockTime;

    /** 删除标志（0代表存在 2代表删除） */
    private String delFlag;

    public Long getAchievementId()
    {
        return achievementId;
    }

    public void setAchievementId(Long achievementId)
    {
        this.achievementId = achievementId;
    }

    public Long getUserId()
    {
        return userId;
    }

    public void setUserId(Long userId)
    {
        this.userId = userId;
    }

    public String getAchievementName()
    {
        return achievementName;
    }

    public void setAchievementName(String achievementName)
    {
        this.achievementName = achievementName;
    }

    public String getAchievementCode()
    {
        return achievementCode;
    }

    public void setAchievementCode(String achievementCode)
    {
        this.achievementCode = achievementCode;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public String getIcon()
    {
        return icon;
    }

    public void setIcon(String icon)
    {
        this.icon = icon;
    }

    public Integer getPointsReward()
    {
        return pointsReward;
    }

    public void setPointsReward(Integer pointsReward)
    {
        this.pointsReward = pointsReward;
    }

    public String getIsUnlocked()
    {
        return isUnlocked;
    }

    public void setIsUnlocked(String isUnlocked)
    {
        this.isUnlocked = isUnlocked;
    }

    public Date getUnlockTime()
    {
        return unlockTime;
    }

    public void setUnlockTime(Date unlockTime)
    {
        this.unlockTime = unlockTime;
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
            .append("achievementId", getAchievementId())
            .append("userId", getUserId())
            .append("achievementName", getAchievementName())
            .append("achievementCode", getAchievementCode())
            .append("description", getDescription())
            .append("icon", getIcon())
            .append("pointsReward", getPointsReward())
            .append("isUnlocked", getIsUnlocked())
            .append("unlockTime", getUnlockTime())
            .append("delFlag", getDelFlag())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }
}
