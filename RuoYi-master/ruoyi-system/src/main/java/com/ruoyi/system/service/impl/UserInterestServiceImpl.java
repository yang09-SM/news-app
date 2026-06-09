package com.ruoyi.system.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.common.core.text.Convert;
import com.ruoyi.system.domain.UserInterest;
import com.ruoyi.system.mapper.UserInterestMapper;
import com.ruoyi.system.service.IUserInterestService;

@Service
public class UserInterestServiceImpl implements IUserInterestService
{
    @Autowired
    private UserInterestMapper userInterestMapper;

    @Override
    public UserInterest selectInterestById(Long interestId)
    {
        return userInterestMapper.selectInterestById(interestId);
    }

    @Override
    public List<UserInterest> selectInterestList(UserInterest userInterest)
    {
        return userInterestMapper.selectInterestList(userInterest);
    }

    @Override
    public int insertInterest(UserInterest userInterest)
    {
        return userInterestMapper.insertInterest(userInterest);
    }

    @Override
    public int updateInterest(UserInterest userInterest)
    {
        return userInterestMapper.updateInterest(userInterest);
    }

    @Override
    public int deleteInterestByIds(String ids)
    {
        return userInterestMapper.deleteInterestByIds(Convert.toStrArray(ids));
    }

    @Override
    public int deleteInterestById(Long interestId)
    {
        return userInterestMapper.deleteInterestById(interestId);
    }
}
