package com.ruoyi.system.service;

import java.util.List;
import com.ruoyi.system.domain.UserCreation;

public interface IUserCreationService
{
    public UserCreation selectCreationById(Long creationId);

    public List<UserCreation> selectCreationList(UserCreation userCreation);

    public int insertCreation(UserCreation userCreation);

    public int updateCreation(UserCreation userCreation);

    public int deleteCreationByIds(String ids);
}
