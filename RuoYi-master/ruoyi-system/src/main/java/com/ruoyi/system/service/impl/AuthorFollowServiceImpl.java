package com.ruoyi.system.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.common.core.text.Convert;
import com.ruoyi.system.domain.AuthorFollow;
import com.ruoyi.system.mapper.AuthorFollowMapper;
import com.ruoyi.system.service.IAuthorFollowService;

@Service
public class AuthorFollowServiceImpl implements IAuthorFollowService
{
    @Autowired
    private AuthorFollowMapper authorFollowMapper;

    @Override
    public AuthorFollow selectAuthorFollowById(Long followId)
    {
        return authorFollowMapper.selectAuthorFollowById(followId);
    }

    @Override
    public List<AuthorFollow> selectAuthorFollowList(AuthorFollow authorFollow)
    {
        return authorFollowMapper.selectAuthorFollowList(authorFollow);
    }

    @Override
    public int insertAuthorFollow(AuthorFollow authorFollow)
    {
        return authorFollowMapper.insertAuthorFollow(authorFollow);
    }

    @Override
    public int updateAuthorFollow(AuthorFollow authorFollow)
    {
        return authorFollowMapper.updateAuthorFollow(authorFollow);
    }

    @Override
    public int deleteAuthorFollowByIds(String ids)
    {
        return authorFollowMapper.deleteAuthorFollowByIds(Convert.toStrArray(ids));
    }
}
