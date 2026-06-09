package com.ruoyi.system.domain;

import java.util.Date;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.core.domain.BaseEntity;

public class ExchangeRecord extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    private Long exchangeId;

    private Long userId;

    private Long productId;

    private String productName;

    private String productImage;

    private Integer pointsCost;

    private Date exchangeTime;

    private String status;

    /** 删除标志（0代表存在 2代表删除） */
    private String delFlag;

    public Long getExchangeId()
    {
        return exchangeId;
    }

    public void setExchangeId(Long exchangeId)
    {
        this.exchangeId = exchangeId;
    }

    public Long getUserId()
    {
        return userId;
    }

    public void setUserId(Long userId)
    {
        this.userId = userId;
    }

    public Long getProductId()
    {
        return productId;
    }

    public void setProductId(Long productId)
    {
        this.productId = productId;
    }

    public String getProductName()
    {
        return productName;
    }

    public void setProductName(String productName)
    {
        this.productName = productName;
    }

    public String getProductImage()
    {
        return productImage;
    }

    public void setProductImage(String productImage)
    {
        this.productImage = productImage;
    }

    public Integer getPointsCost()
    {
        return pointsCost;
    }

    public void setPointsCost(Integer pointsCost)
    {
        this.pointsCost = pointsCost;
    }

    public Date getExchangeTime()
    {
        return exchangeTime;
    }

    public void setExchangeTime(Date exchangeTime)
    {
        this.exchangeTime = exchangeTime;
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
            .append("exchangeId", getExchangeId())
            .append("userId", getUserId())
            .append("productId", getProductId())
            .append("productName", getProductName())
            .append("productImage", getProductImage())
            .append("pointsCost", getPointsCost())
            .append("exchangeTime", getExchangeTime())
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
