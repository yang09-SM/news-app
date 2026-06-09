package com.ruoyi.system.service;

import java.util.List;
import com.ruoyi.system.domain.UserInterest;

public interface IUserInterestService
{
    public UserInterest selectInterestById(Long interestId);

    public List<UserInterest> selectInterestList(UserInterest userInterest);

    public int insertInterest(UserInterest userInterest);

    public int updateInterest(UserInterest userInterest);

    public int deleteInterestByIds(String ids);

    public int deleteInterestById(Long interestId);
}
