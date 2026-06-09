package com.ruoyi.system.service;

import java.util.List;
import com.ruoyi.system.domain.UserAchievement;

public interface IUserAchievementService
{
    public UserAchievement selectAchievementById(Long achievementId);

    public List<UserAchievement> selectAchievementList(UserAchievement userAchievement);

    public int insertAchievement(UserAchievement userAchievement);

    public int updateAchievement(UserAchievement userAchievement);

    public int deleteAchievementByIds(String ids);
}
