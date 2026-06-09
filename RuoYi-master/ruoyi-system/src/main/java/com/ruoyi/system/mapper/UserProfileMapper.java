package com.ruoyi.system.mapper;

import java.util.List;
import com.ruoyi.system.domain.UserProfile;

public interface UserProfileMapper
{
    public UserProfile selectUserProfileByUserId(Long userId);

    public UserProfile selectUserProfileById(Long profileId);

    public List<UserProfile> selectUserProfileList(UserProfile userProfile);

    public int insertUserProfile(UserProfile userProfile);

    public int updateUserProfile(UserProfile userProfile);
}
