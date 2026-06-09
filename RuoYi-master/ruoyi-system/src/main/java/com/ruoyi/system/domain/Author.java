package com.ruoyi.system.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 作者表 author
 *
 * @author ruoyi
 */
public class Author extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 作者ID */
    private Long authorId;

    /** 作者名称 */
    private String authorName;

    /** 头像 */
    private String avatar;

    /** 简介 */
    private String bio;

    /** 粉丝数量 */
    private Long followerCount;

    /** 文章数量 */
    private Long articleCount;

    /** 状态（0正常 1停用） */
    private String status;

    /** 删除标志（0代表存在 2代表删除） */
    private String delFlag;

    public Long getAuthorId()
    {
        return authorId;
    }

    public void setAuthorId(Long authorId)
    {
        this.authorId = authorId;
    }

    public String getAuthorName()
    {
        return authorName;
    }

    public void setAuthorName(String authorName)
    {
        this.authorName = authorName;
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

    public Long getFollowerCount()
    {
        return followerCount;
    }

    public void setFollowerCount(Long followerCount)
    {
        this.followerCount = followerCount;
    }

    public Long getArticleCount()
    {
        return articleCount;
    }

    public void setArticleCount(Long articleCount)
    {
        this.articleCount = articleCount;
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
            .append("authorId", getAuthorId())
            .append("authorName", getAuthorName())
            .append("avatar", getAvatar())
            .append("bio", getBio())
            .append("followerCount", getFollowerCount())
            .append("articleCount", getArticleCount())
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
