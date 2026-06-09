package com.ruoyi.system.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.domain.UserProfile;
import com.ruoyi.system.mapper.UserProfileMapper;
import com.ruoyi.system.service.IUserProfileService;

@Service
public class UserProfileServiceImpl implements IUserProfileService
{
    @Autowired
    private UserProfileMapper userProfileMapper;

    @Override
    public UserProfile selectUserProfileByUserId(Long userId)
    {
        return userProfileMapper.selectUserProfileByUserId(userId);
    }

    @Override
    public UserProfile selectUserProfileById(Long profileId)
    {
        return userProfileMapper.selectUserProfileById(profileId);
    }

    @Override
    public List<UserProfile> selectUserProfileList(UserProfile userProfile)
    {
        return userProfileMapper.selectUserProfileList(userProfile);
    }

    @Override
    public int insertUserProfile(UserProfile userProfile)
    {
        return userProfileMapper.insertUserProfile(userProfile);
    }

    @Override
    public int updateUserProfile(UserProfile userProfile)
    {
        return userProfileMapper.updateUserProfile(userProfile);
    }
}
