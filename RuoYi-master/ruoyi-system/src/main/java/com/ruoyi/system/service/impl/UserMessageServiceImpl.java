package com.ruoyi.system.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.common.core.text.Convert;
import com.ruoyi.system.domain.UserMessage;
import com.ruoyi.system.mapper.UserMessageMapper;
import com.ruoyi.system.service.IUserMessageService;

@Service
public class UserMessageServiceImpl implements IUserMessageService
{
    @Autowired
    private UserMessageMapper userMessageMapper;

    @Override
    public UserMessage selectMessageById(Long messageId)
    {
        return userMessageMapper.selectMessageById(messageId);
    }

    @Override
    public List<UserMessage> selectMessageList(UserMessage userMessage)
    {
        return userMessageMapper.selectMessageList(userMessage);
    }

    @Override
    public int insertMessage(UserMessage userMessage)
    {
        return userMessageMapper.insertMessage(userMessage);
    }

    @Override
    public int updateMessage(UserMessage userMessage)
    {
        return userMessageMapper.updateMessage(userMessage);
    }

    @Override
    public int deleteMessageByIds(String ids)
    {
        return userMessageMapper.deleteMessageByIds(Convert.toStrArray(ids));
    }
}
