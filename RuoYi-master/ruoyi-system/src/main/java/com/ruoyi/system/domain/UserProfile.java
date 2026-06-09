package com.ruoyi.system.domain;

import java.math.BigDecimal;
import java.util.Date;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.core.domain.BaseEntity;

public class UserProfile extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    private Long profileId;

    private Long userId;

    private String nickname;

    private String avatar;

    private String bio;

    private Integer points;

    private BigDecimal cashBalance;

    private Date lastCheckin;

    private Integer checkinDays;

    private Integer followingCount;

    private Integer followersCount;

    private Integer friendsCount;

    private Integer likesCount;

    private Integer level;

    private Integer vipLevel;

    private Date vipExpireTime;

    /** 删除标志（0代表存在 2代表删除） */
    private String delFlag;

    public Long getProfileId()
    {
        return profileId;
    }

    public void setProfileId(Long profileId)
    {
        this.profileId = profileId;
    }

    public Long getUserId()
    {
        return userId;
    }

    public void setUserId(Long userId)
    {
        this.userId = userId;
    }

    public String getNickname()
    {
        return nickname;
    }

    public void setNickname(String nickname)
    {
        this.nickname = nickname;
    }

    public String getAvatar()
    {
        return avatar;
    }

    public void setAvatar(String avatar)
    {
        this.avatar = avatar;
    }

    public String getBio()
    {
        return bio;
    }

    public void setBio(String bio)
    {
        this.bio = bio;
    }

    public Integer getPoints()
    {
        return points;
    }

    public void setPoints(Integer points)
    {
        this.points = points;
    }

    public BigDecimal getCashBalance()
    {
        return cashBalance;
    }

    public void setCashBalance(BigDecimal cashBalance)
    {
        this.cashBalance = cashBalance;
    }

    public Date getLastCheckin()
    {
        return lastCheckin;
    }

    public void setLastCheckin(Date lastCheckin)
    {
        this.lastCheckin = lastCheckin;
    }

    public Integer getCheckinDays()
    {
        return checkinDays;
    }

    public void setCheckinDays(Integer checkinDays)
    {
        this.checkinDays = checkinDays;
    }

    public Integer getFollowingCount()
    {
        return followingCount;
    }

    public void setFollowingCount(Integer followingCount)
    {
        this.followingCount = followingCount;
    }

    public Integer getFollowersCount()
    {
        return followersCount;
    }

    public void setFollowersCount(Integer followersCount)
    {
        this.followersCount = followersCount;
    }

    public Integer getFriendsCount()
    {
        return friendsCount;
    }

    public void setFriendsCount(Integer friendsCount)
    {
        this.friendsCount = friendsCount;
    }

    public Integer getLikesCount()
    {
        return likesCount;
    }

    public void setLikesCount(Integer likesCount)
    {
        this.likesCount = likesCount;
    }

    public Integer getLevel()
    {
        return level;
    }

    public void setLevel(Integer level)
    {
        this.level = level;
    }

    public Integer getVipLevel()
    {
        return vipLevel;
    }

    public void setVipLevel(Integer vipLevel)
    {
        this.vipLevel = vipLevel;
    }

    public Date getVipExpireTime()
    {
        return vipExpireTime;
    }

    public void setVipExpireTime(Date vipExpireTime)
    {
        this.vipExpireTime = vipExpireTime;
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
            .append("profileId", getProfileId())
            .append("userId", getUserId())
            .append("nickname", getNickname())
            .append("avatar", getAvatar())
            .append("bio", getBio())
            .append("points", getPoints())
            .append("cashBalance", getCashBalance())
            .append("lastCheckin", getLastCheckin())
            .append("checkinDays", getCheckinDays())
            .append("followingCount", getFollowingCount())
            .append("followersCount", getFollowersCount())
            .append("friendsCount", getFriendsCount())
            .append("likesCount", getLikesCount())
            .append("level", getLevel())
            .append("vipLevel", getVipLevel())
            .append("vipExpireTime", getVipExpireTime())
            .append("delFlag", getDelFlag())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }
}
