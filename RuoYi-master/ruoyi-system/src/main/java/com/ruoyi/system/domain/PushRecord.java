package com.ruoyi.system.domain;

import java.util.Date;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 推送记录对象 push_record
 *
 * @author ruoyi
 */
public class PushRecord extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 推送ID */
    private Long pushId;

    /** 推送标题 */
    private String title;

    /** 推送内容 */
    private String content;

    /** 推送类型（all/tag/alias/single） */
    private String pushType;

    /** 推送目标值 */
    private String targetValue;

    /** 发送状态（0待发送 1已发送 2发送失败） */
    private String sendStatus;

    /** 发送人数 */
    private Integer sendCount;

    /** 到达人数 */
    private Integer arriveCount;

    /** 点击人数 */
    private Integer clickCount;

    /** 发送时间 */
    private Date sendTime;

    /** 删除标志（0代表存在 2代表删除） */
    private String delFlag;

    public Long getPushId()
    {
        return pushId;
    }

    public void setPushId(Long pushId)
    {
        this.pushId = pushId;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public String getContent()
    {
        return content;
    }

    public void setContent(String content)
    {
        this.content = content;
    }

    public String getPushType()
    {
        return pushType;
    }

    public void setPushType(String pushType)
    {
        this.pushType = pushType;
    }

    public String getTargetValue()
    {
        return targetValue;
    }

    public void setTargetValue(String targetValue)
    {
        this.targetValue = targetValue;
    }

    public String getSendStatus()
    {
        return sendStatus;
    }

    public void setSendStatus(String sendStatus)
    {
        this.sendStatus = sendStatus;
    }

    public Integer getSendCount()
    {
        return sendCount;
    }

    public void setSendCount(Integer sendCount)
    {
        this.sendCount = sendCount;
    }

    public Integer getArriveCount()
    {
        return arriveCount;
    }

    public void setArriveCount(Integer arriveCount)
    {
        this.arriveCount = arriveCount;
    }

    public Integer getClickCount()
    {
        return clickCount;
    }

    public void setClickCount(Integer clickCount)
    {
        this.clickCount = clickCount;
    }

    public Date getSendTime()
    {
        return sendTime;
    }

    public void setSendTime(Date sendTime)
    {
        this.sendTime = sendTime;
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
            .append("pushId", getPushId())
            .append("title", getTitle())
            .append("content", getContent())
            .append("pushType", getPushType())
            .append("targetValue", getTargetValue())
            .append("sendStatus", getSendStatus())
            .append("sendCount", getSendCount())
            .append("arriveCount", getArriveCount())
            .append("clickCount", getClickCount())
            .append("sendTime", getSendTime())
            .append("delFlag", getDelFlag())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }
}
