package com.ruoyi.system.mapper;

import java.util.List;
import com.ruoyi.system.domain.UserInterest;

public interface UserInterestMapper
{
    public UserInterest selectInterestById(Long interestId);

    public List<UserInterest> selectInterestList(UserInterest userInterest);

    public int insertInterest(UserInterest userInterest);

    public int updateInterest(UserInterest userInterest);

    public int deleteInterestByIds(String[] interestIds);

    public int deleteInterestById(Long interestId);
}
