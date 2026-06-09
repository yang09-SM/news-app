package com.ruoyi.system.domain;

import java.util.Date;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.core.domain.BaseEntity;

public class Report extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    private Long reportId;

    private Long reporterId;

    private String reportType;

    private Long targetId;

    private String reason;

    private String description;

    private String images;

    private String status;

    private String handleResult;

    private Long handleUserId;

    private Date handleTime;

    /** 删除标志（0代表存在 2代表删除） */
    private String delFlag;

    public Long getReportId()
    {
        return reportId;
    }

    public void setReportId(Long reportId)
    {
        this.reportId = reportId;
    }

    public Long getReporterId()
    {
        return reporterId;
    }

    public void setReporterId(Long reporterId)
    {
        this.reporterId = reporterId;
    }

    public String getReportType()
    {
        return reportType;
    }

    public void setReportType(String reportType)
    {
        this.reportType = reportType;
    }

    public Long getTargetId()
    {
        return targetId;
    }

    public void setTargetId(Long targetId)
    {
        this.targetId = targetId;
    }

    public String getReason()
    {
        return reason;
    }

    public void setReason(String reason)
    {
        this.reason = reason;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public String getImages()
    {
        return images;
    }

    public void setImages(String images)
    {
        this.images = images;
    }

    public String getStatus()
    {
        return status;
    }

    public void setStatus(String status)
    {
        this.status = status;
    }

    public String getHandleResult()
    {
        return handleResult;
    }

    public void setHandleResult(String handleResult)
    {
        this.handleResult = handleResult;
    }

    public Long getHandleUserId()
    {
        return handleUserId;
    }

    public void setHandleUserId(Long handleUserId)
    {
        this.handleUserId = handleUserId;
    }

    public Date getHandleTime()
    {
        return handleTime;
    }

    public void setHandleTime(Date handleTime)
    {
        this.handleTime = handleTime;
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
            .append("reportId", getReportId())
            .append("reporterId", getReporterId())
            .append("reportType", getReportType())
            .append("targetId", getTargetId())
            .append("reason", getReason())
            .append("description", getDescription())
            .append("images", getImages())
            .append("status", getStatus())
            .append("handleResult", getHandleResult())
            .append("handleUserId", getHandleUserId())
            .append("handleTime", getHandleTime())
            .append("delFlag", getDelFlag())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }
}
