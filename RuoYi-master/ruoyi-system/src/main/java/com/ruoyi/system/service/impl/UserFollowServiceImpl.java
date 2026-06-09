package com.ruoyi.system.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.common.core.text.Convert;
import com.ruoyi.system.domain.UserFollow;
import com.ruoyi.system.mapper.UserFollowMapper;
import com.ruoyi.system.service.IUserFollowService;

@Service
public class UserFollowServiceImpl implements IUserFollowService
{
    @Autowired
    private UserFollowMapper userFollowMapper;

    @Override
    public UserFollow selectFollowById(Long followId)
    {
        return userFollowMapper.selectFollowById(followId);
    }

    @Override
    public List<UserFollow> selectFollowList(UserFollow userFollow)
    {
        return userFollowMapper.selectFollowList(userFollow);
    }

    @Override
    public int insertFollow(UserFollow userFollow)
    {
        return userFollowMapper.insertFollow(userFollow);
    }

    @Override
    public int updateFollow(UserFollow userFollow)
    {
        return userFollowMapper.updateFollow(userFollow);
    }

    @Override
    public int deleteFollowByIds(String ids)
    {
        return userFollowMapper.deleteFollowByIds(Convert.toStrArray(ids));
    }

    @Override
    public UserFollow selectFollowByUserAndTarget(Long userId, Long followUserId)
    {
        return userFollowMapper.selectFollowByUserAndTarget(userId, followUserId);
    }

    @Override
    public List<UserFollow> selectFollowersByUserId(Long userId)
    {
        return userFollowMapper.selectFollowersByUserId(userId);
    }

    @Override
    public List<UserFollow> selectFollowingByUserId(Long userId)
    {
        return userFollowMapper.selectFollowingByUserId(userId);
    }

    @Override
    public int deleteFollowById(Long followId)
    {
        return userFollowMapper.deleteFollowById(followId);
    }
}
