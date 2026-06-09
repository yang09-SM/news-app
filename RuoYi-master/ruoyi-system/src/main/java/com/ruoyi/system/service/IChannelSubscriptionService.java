package com.ruoyi.system.service;

import java.util.List;
import com.ruoyi.system.domain.ChannelSubscription;

public interface IChannelSubscriptionService
{
    public ChannelSubscription selectSubscriptionById(Long subscriptionId);

    public List<ChannelSubscription> selectSubscriptionList(ChannelSubscription channelSubscription);

    public int insertSubscription(ChannelSubscription channelSubscription);

    public int updateSubscription(ChannelSubscription channelSubscription);

    public int deleteSubscriptionByIds(String ids);
}
