package com.ruoyi.system.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.common.core.text.Convert;
import com.ruoyi.system.domain.UserAchievement;
import com.ruoyi.system.mapper.UserAchievementMapper;
import com.ruoyi.system.service.IUserAchievementService;

@Service
public class UserAchievementServiceImpl implements IUserAchievementService
{
    @Autowired
    private UserAchievementMapper userAchievementMapper;

    @Override
    public UserAchievement selectAchievementById(Long achievementId)
    {
        return userAchievementMapper.selectAchievementById(achievementId);
    }

    @Override
    public List<UserAchievement> selectAchievementList(UserAchievement userAchievement)
    {
        return userAchievementMapper.selectAchievementList(userAchievement);
    }

    @Override
    public int insertAchievement(UserAchievement userAchievement)
    {
        return userAchievementMapper.insertAchievement(userAchievement);
    }

    @Override
    public int updateAchievement(UserAchievement userAchievement)
    {
        return userAchievementMapper.updateAchievement(userAchievement);
    }

    @Override
    public int deleteAchievementByIds(String ids)
    {
        return userAchievementMapper.deleteAchievementByIds(Convert.toStrArray(ids));
    }
}
