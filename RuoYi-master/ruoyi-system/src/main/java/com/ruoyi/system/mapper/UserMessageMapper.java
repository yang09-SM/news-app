package com.ruoyi.system.mapper;

import java.util.List;
import com.ruoyi.system.domain.UserMessage;

public interface UserMessageMapper
{
    public UserMessage selectMessageById(Long messageId);

    public List<UserMessage> selectMessageList(UserMessage userMessage);

    public int insertMessage(UserMessage userMessage);

    public int updateMessage(UserMessage userMessage);

    public int deleteMessageByIds(String[] messageIds);
}
