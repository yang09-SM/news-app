package com.ruoyi.system.mapper;

import java.util.List;
import com.ruoyi.system.domain.AuthorFollow;

public interface AuthorFollowMapper
{
    public AuthorFollow selectAuthorFollowById(Long followId);

    public List<AuthorFollow> selectAuthorFollowList(AuthorFollow authorFollow);

    public int insertAuthorFollow(AuthorFollow authorFollow);

    public int updateAuthorFollow(AuthorFollow authorFollow);

    public int deleteAuthorFollowByIds(String[] followIds);
}
