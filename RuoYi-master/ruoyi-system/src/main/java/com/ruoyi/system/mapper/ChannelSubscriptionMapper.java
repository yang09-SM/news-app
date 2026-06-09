package com.ruoyi.system.mapper;

import java.util.List;
import com.ruoyi.system.domain.ChannelSubscription;

public interface ChannelSubscriptionMapper
{
    public ChannelSubscription selectSubscriptionById(Long subscriptionId);

    public List<ChannelSubscription> selectSubscriptionList(ChannelSubscription channelSubscription);

    public int insertSubscription(ChannelSubscription channelSubscription);

    public int updateSubscription(ChannelSubscription channelSubscription);

    public int deleteSubscriptionByIds(String[] subscriptionIds);
}
