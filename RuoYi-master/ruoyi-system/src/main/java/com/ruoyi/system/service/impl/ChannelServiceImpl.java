package com.ruoyi.system.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.common.core.text.Convert;
import com.ruoyi.system.domain.Channel;
import com.ruoyi.system.mapper.ChannelMapper;
import com.ruoyi.system.service.IChannelService;

/**
 * 频道 服务层实现
 *
 * @author ruoyi
 */
@Service
public class ChannelServiceImpl implements IChannelService
{
    @Autowired
    private ChannelMapper channelMapper;

    /**
     * 查询频道信息
     *
     * @param channelId 频道ID
     * @return 频道信息
     */
    @Override
    public Channel selectChannelById(Long channelId)
    {
        return channelMapper.selectChannelById(channelId);
    }

    /**
     * 查询频道列表
     *
     * @param channel 频道信息
     * @return 频道集合
     */
    @Override
    public List<Channel> selectChannelList(Channel channel)
    {
        return channelMapper.selectChannelList(channel);
    }

    /**
     * 新增频道
     *
     * @param channel 频道信息
     * @return 结果
     */
    @Override
    public int insertChannel(Channel channel)
    {
        return channelMapper.insertChannel(channel);
    }

    /**
     * 修改频道
     *
     * @param channel 频道信息
     * @return 结果
     */
    @Override
    public int updateChannel(Channel channel)
    {
        return channelMapper.updateChannel(channel);
    }

    /**
     * 删除频道对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteChannelByIds(String ids)
    {
        return channelMapper.deleteChannelByIds(Convert.toStrArray(ids));
    }
}
