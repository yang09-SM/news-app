package com.ruoyi.system.service;

import java.util.List;
import com.ruoyi.system.domain.Channel;

/**
 * 频道 服务层
 *
 * @author ruoyi
 */
public interface IChannelService
{
    /**
     * 查询频道信息
     *
     * @param channelId 频道ID
     * @return 频道信息
     */
    public Channel selectChannelById(Long channelId);

    /**
     * 查询频道列表
     *
     * @param channel 频道信息
     * @return 频道集合
     */
    public List<Channel> selectChannelList(Channel channel);

    /**
     * 新增频道
     *
     * @param channel 频道信息
     * @return 结果
     */
    public int insertChannel(Channel channel);

    /**
     * 修改频道
     *
     * @param channel 频道信息
     * @return 结果
     */
    public int updateChannel(Channel channel);

    /**
     * 删除频道对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteChannelByIds(String ids);
}
