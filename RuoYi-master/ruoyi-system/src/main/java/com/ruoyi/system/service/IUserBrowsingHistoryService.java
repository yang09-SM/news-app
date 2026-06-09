package com.ruoyi.system.service;

import java.util.List;
import com.ruoyi.system.domain.UserBrowsingHistory;

public interface IUserBrowsingHistoryService
{
    public UserBrowsingHistory selectBrowsingHistoryById(Long historyId);

    public List<UserBrowsingHistory> selectBrowsingHistoryList(UserBrowsingHistory userBrowsingHistory);

    public int insertBrowsingHistory(UserBrowsingHistory userBrowsingHistory);

    public int updateBrowsingHistory(UserBrowsingHistory userBrowsingHistory);

    public int deleteBrowsingHistoryByIds(String ids);

    public int deleteBrowsingHistoryByUserId(Long userId);
}
