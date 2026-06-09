package com.ruoyi.system.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.common.core.text.Convert;
import com.ruoyi.system.domain.ChannelSubscription;
import com.ruoyi.system.mapper.ChannelSubscriptionMapper;
import com.ruoyi.system.service.IChannelSubscriptionService;

@Service
public class ChannelSubscriptionServiceImpl implements IChannelSubscriptionService
{
    @Autowired
    private ChannelSubscriptionMapper channelSubscriptionMapper;

    @Override
    public ChannelSubscription selectSubscriptionById(Long subscriptionId)
    {
        return channelSubscriptionMapper.selectSubscriptionById(subscriptionId);
    }

    @Override
    public List<ChannelSubscription> selectSubscriptionList(ChannelSubscription channelSubscription)
    {
        return channelSubscriptionMapper.selectSubscriptionList(channelSubscription);
    }

    @Override
    public int insertSubscription(ChannelSubscription channelSubscription)
    {
        return channelSubscriptionMapper.insertSubscription(channelSubscription);
    }

    @Override
    public int updateSubscription(ChannelSubscription channelSubscription)
    {
        return channelSubscriptionMapper.updateSubscription(channelSubscription);
    }

    @Override
    public int deleteSubscriptionByIds(String ids)
    {
        return channelSubscriptionMapper.deleteSubscriptionByIds(Convert.toStrArray(ids));
    }
}
