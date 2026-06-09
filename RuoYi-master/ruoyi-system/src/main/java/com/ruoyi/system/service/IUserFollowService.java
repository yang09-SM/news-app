package com.ruoyi.system.service;

import java.util.List;
import com.ruoyi.system.domain.UserFollow;

public interface IUserFollowService
{
    public UserFollow selectFollowById(Long followId);

    public List<UserFollow> selectFollowList(UserFollow userFollow);

    public int insertFollow(UserFollow userFollow);

    public int updateFollow(UserFollow userFollow);

    public int deleteFollowByIds(String ids);

    public UserFollow selectFollowByUserAndTarget(Long userId, Long followUserId);

    public List<UserFollow> selectFollowersByUserId(Long userId);

    public List<UserFollow> selectFollowingByUserId(Long userId);

    public int deleteFollowById(Long followId);
}
