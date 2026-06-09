package com.ruoyi.system.mapper;

import java.util.List;
import com.ruoyi.system.domain.UserCreation;

public interface UserCreationMapper
{
    public UserCreation selectCreationById(Long creationId);

    public List<UserCreation> selectCreationList(UserCreation userCreation);

    public int insertCreation(UserCreation userCreation);

    public int updateCreation(UserCreation userCreation);

    public int deleteCreationByIds(String[] creationIds);
}
