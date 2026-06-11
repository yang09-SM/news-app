package com.ruoyi.system.service.impl;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.common.core.text.Convert;
import com.ruoyi.common.core.push.JPushService;
import com.ruoyi.system.domain.UserMessage;
import com.ruoyi.system.mapper.UserMessageMapper;
import com.ruoyi.system.service.IUserMessageService;

@Service
public class UserMessageServiceImpl implements IUserMessageService
{
    private static final Logger log = LoggerFactory.getLogger(UserMessageServiceImpl.class);

    @Autowired
    private UserMessageMapper userMessageMapper;

    @Autowired
    private JPushService jPushService;

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
        int result = userMessageMapper.insertMessage(userMessage);
        // 消息保存成功后，如果推送已启用，则发送极光推送通知
        if (result > 0 && jPushService.isPushEnabled() && userMessage.getUserId() != null)
        {
            try
            {
                // 使用用户ID作为别名进行推送
                jPushService.pushByAlias(
                    userMessage.getUserId().toString(),
                    userMessage.getTitle(),
                    userMessage.getContent()
                );
                log.info("消息推送成功: userId={}, title={}", userMessage.getUserId(), userMessage.getTitle());
            }
            catch (Exception e)
            {
                log.error("消息推送失败: userId={}, title={}", userMessage.getUserId(), userMessage.getTitle(), e);
            }
        }
        return result;
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
