package com.ruoyi.system.service;

import java.util.List;
import com.ruoyi.system.domain.UserProfile;

public interface IUserProfileService
{
    public UserProfile selectUserProfileByUserId(Long userId);

    public UserProfile selectUserProfileById(Long profileId);

    public List<UserProfile> selectUserProfileList(UserProfile userProfile);

    public int insertUserProfile(UserProfile userProfile);

    public int updateUserProfile(UserProfile userProfile);
}
