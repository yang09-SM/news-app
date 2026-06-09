package com.ruoyi.system.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.common.core.text.Convert;
import com.ruoyi.system.domain.UserCreation;
import com.ruoyi.system.mapper.UserCreationMapper;
import com.ruoyi.system.service.IUserCreationService;

@Service
public class UserCreationServiceImpl implements IUserCreationService
{
    @Autowired
    private UserCreationMapper userCreationMapper;

    @Override
    public UserCreation selectCreationById(Long creationId)
    {
        return userCreationMapper.selectCreationById(creationId);
    }

    @Override
    public List<UserCreation> selectCreationList(UserCreation userCreation)
    {
        return userCreationMapper.selectCreationList(userCreation);
    }

    @Override
    public int insertCreation(UserCreation userCreation)
    {
        return userCreationMapper.insertCreation(userCreation);
    }

    @Override
    public int updateCreation(UserCreation userCreation)
    {
        return userCreationMapper.updateCreation(userCreation);
    }

    @Override
    public int deleteCreationByIds(String ids)
    {
        return userCreationMapper.deleteCreationByIds(Convert.toStrArray(ids));
    }
}
