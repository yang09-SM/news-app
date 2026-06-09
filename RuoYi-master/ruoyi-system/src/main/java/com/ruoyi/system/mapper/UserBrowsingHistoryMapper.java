package com.ruoyi.system.mapper;

import java.util.List;
import com.ruoyi.system.domain.UserBrowsingHistory;

public interface UserBrowsingHistoryMapper
{
    public UserBrowsingHistory selectBrowsingHistoryById(Long historyId);

    public List<UserBrowsingHistory> selectBrowsingHistoryList(UserBrowsingHistory userBrowsingHistory);

    public int insertBrowsingHistory(UserBrowsingHistory userBrowsingHistory);

    public int updateBrowsingHistory(UserBrowsingHistory userBrowsingHistory);

    public int deleteBrowsingHistoryByIds(String[] historyIds);

    public int deleteBrowsingHistoryByUserId(Long userId);
}
