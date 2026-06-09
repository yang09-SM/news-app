package com.ruoyi.system.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.common.core.text.Convert;
import com.ruoyi.system.domain.Topic;
import com.ruoyi.system.mapper.TopicMapper;
import com.ruoyi.system.service.ITopicService;

@Service
public class TopicServiceImpl implements ITopicService
{
    @Autowired
    private TopicMapper topicMapper;

    @Override
    public Topic selectTopicById(Long topicId)
    {
        return topicMapper.selectTopicById(topicId);
    }

    @Override
    public List<Topic> selectTopicList(Topic topic)
    {
        return topicMapper.selectTopicList(topic);
    }

    @Override
    public int insertTopic(Topic topic)
    {
        return topicMapper.insertTopic(topic);
    }

    @Override
    public int updateTopic(Topic topic)
    {
        return topicMapper.updateTopic(topic);
    }

    @Override
    public int deleteTopicByIds(String ids)
    {
        return topicMapper.deleteTopicByIds(Convert.toStrArray(ids));
    }
}
