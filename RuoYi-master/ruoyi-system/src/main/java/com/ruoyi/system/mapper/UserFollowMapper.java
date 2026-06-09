package com.ruoyi.system.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import com.ruoyi.system.domain.UserFollow;

public interface UserFollowMapper
{
    public UserFollow selectFollowById(Long followId);

    public List<UserFollow> selectFollowList(UserFollow userFollow);

    public int insertFollow(UserFollow userFollow);

    public int updateFollow(UserFollow userFollow);

    public int deleteFollowByIds(String[] followIds);

    public UserFollow selectFollowByUserAndTarget(@Param("userId") Long userId, @Param("followUserId") Long followUserId);

    public List<UserFollow> selectFollowersByUserId(@Param("userId") Long userId);

    public List<UserFollow> selectFollowingByUserId(@Param("userId") Long userId);

    public int deleteFollowById(Long followId);
}
