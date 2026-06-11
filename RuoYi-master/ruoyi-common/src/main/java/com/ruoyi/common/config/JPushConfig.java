package com.ruoyi.common.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 极光推送配置类
 *
 * @author ruoyi
 */
@Component
@ConfigurationProperties(prefix = "jpush")
public class JPushConfig
{
    /** 极光推送AppKey */
    private String appKey;

    /** 极光推送MasterSecret */
    private String masterSecret;

    /** 是否启用推送（默认false） */
    private boolean enabled;

    /** iOS环境: 0开发 1生产 */
    private int apnsProduction;

    public String getAppKey()
    {
        return appKey;
    }

    public void setAppKey(String appKey)
    {
        this.appKey = appKey;
    }

    public String getMasterSecret()
    {
        return masterSecret;
    }

    public void setMasterSecret(String masterSecret)
    {
        this.masterSecret = masterSecret;
    }

    public boolean isEnabled()
    {
        return enabled;
    }

    public void setEnabled(boolean enabled)
    {
        this.enabled = enabled;
    }

    public int getApnsProduction()
    {
        return apnsProduction;
    }

    public void setApnsProduction(int apnsProduction)
    {
        this.apnsProduction = apnsProduction;
    }
}
