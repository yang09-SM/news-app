package com.ruoyi.system.domain;

import java.util.Date;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.core.domain.BaseEntity;

public class UserComment extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    private Long commentId;

    private Long articleId;

    private Long userId;

    private String userName;

    private String userAvatar;

    private String content;

    private Long parentId;

    private Long replyToUserId;

    private String replyToUserName;

    private Integer likeCount;

    private Integer replyCount;

    private String isTop;

    private String status;

    /** 删除标志（0代表存在 2代表删除） */
    private String delFlag;

    public Long getCommentId()
    {
        return commentId;
    }

    public void setCommentId(Long commentId)
    {
        this.commentId = commentId;
    }

    public Long getArticleId()
    {
        return articleId;
    }

    public void setArticleId(Long articleId)
    {
        this.articleId = articleId;
    }

    public Long getUserId()
    {
        return userId;
    }

    public void setUserId(Long userId)
    {
        this.userId = userId;
    }

    public String getUserName()
    {
        return userName;
    }

    public void setUserName(String userName)
    {
        this.userName = userName;
    }

    public String getUserAvatar()
    {
        return userAvatar;
    }

    public void setUserAvatar(String userAvatar)
    {
        this.userAvatar = userAvatar;
    }

    public String getContent()
    {
        return content;
    }

    public void setContent(String content)
    {
        this.content = content;
    }

    public Long getParentId()
    {
        return parentId;
    }

    public void setParentId(Long parentId)
    {
        this.parentId = parentId;
    }

    public Long getReplyToUserId()
    {
        return replyToUserId;
    }

    public void setReplyToUserId(Long replyToUserId)
    {
        this.replyToUserId = replyToUserId;
    }

    public String getReplyToUserName()
    {
        return replyToUserName;
    }

    public void setReplyToUserName(String replyToUserName)
    {
        this.replyToUserName = replyToUserName;
    }

    public Integer getLikeCount()
    {
        return likeCount;
    }

    public void setLikeCount(Integer likeCount)
    {
        this.likeCount = likeCount;
    }

    public Integer getReplyCount()
    {
        return replyCount;
    }

    public void setReplyCount(Integer replyCount)
    {
        this.replyCount = replyCount;
    }

    public String getIsTop()
    {
        return isTop;
    }

    public void setIsTop(String isTop)
    {
        this.isTop = isTop;
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
            .append("commentId", getCommentId())
            .append("articleId", getArticleId())
            .append("userId", getUserId())
            .append("userName", getUserName())
            .append("userAvatar", getUserAvatar())
            .append("content", getContent())
            .append("parentId", getParentId())
            .append("replyToUserId", getReplyToUserId())
            .append("replyToUserName", getReplyToUserName())
            .append("likeCount", getLikeCount())
            .append("replyCount", getReplyCount())
            .append("isTop", getIsTop())
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
