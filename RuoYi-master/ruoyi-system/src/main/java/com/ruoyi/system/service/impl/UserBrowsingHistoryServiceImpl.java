package com.ruoyi.system.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.common.core.text.Convert;
import com.ruoyi.system.domain.UserBrowsingHistory;
import com.ruoyi.system.mapper.UserBrowsingHistoryMapper;
import com.ruoyi.system.service.IUserBrowsingHistoryService;

@Service
public class UserBrowsingHistoryServiceImpl implements IUserBrowsingHistoryService
{
    @Autowired
    private UserBrowsingHistoryMapper userBrowsingHistoryMapper;

    @Override
    public UserBrowsingHistory selectBrowsingHistoryById(Long historyId)
    {
        return userBrowsingHistoryMapper.selectBrowsingHistoryById(historyId);
    }

    @Override
    public List<UserBrowsingHistory> selectBrowsingHistoryList(UserBrowsingHistory userBrowsingHistory)
    {
        return userBrowsingHistoryMapper.selectBrowsingHistoryList(userBrowsingHistory);
    }

    @Override
    public int insertBrowsingHistory(UserBrowsingHistory userBrowsingHistory)
    {
        return userBrowsingHistoryMapper.insertBrowsingHistory(userBrowsingHistory);
    }

    @Override
    public int updateBrowsingHistory(UserBrowsingHistory userBrowsingHistory)
    {
        return userBrowsingHistoryMapper.updateBrowsingHistory(userBrowsingHistory);
    }

    @Override
    public int deleteBrowsingHistoryByIds(String ids)
    {
        return userBrowsingHistoryMapper.deleteBrowsingHistoryByIds(Convert.toStrArray(ids));
    }

    @Override
    public int deleteBrowsingHistoryByUserId(Long userId)
    {
        return userBrowsingHistoryMapper.deleteBrowsingHistoryByUserId(userId);
    }
}
