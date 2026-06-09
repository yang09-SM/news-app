package com.ruoyi.system.service;

import java.util.List;
import com.ruoyi.system.domain.Topic;

public interface ITopicService
{
    public Topic selectTopicById(Long topicId);

    public List<Topic> selectTopicList(Topic topic);

    public int insertTopic(Topic topic);

    public int updateTopic(Topic topic);

    public int deleteTopicByIds(String ids);
}
