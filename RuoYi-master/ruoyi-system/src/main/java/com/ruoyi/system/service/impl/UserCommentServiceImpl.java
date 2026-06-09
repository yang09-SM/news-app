package com.ruoyi.system.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.common.core.text.Convert;
import com.ruoyi.system.domain.UserComment;
import com.ruoyi.system.mapper.UserCommentMapper;
import com.ruoyi.system.service.IUserCommentService;

@Service
public class UserCommentServiceImpl implements IUserCommentService
{
    @Autowired
    private UserCommentMapper userCommentMapper;

    @Override
    public UserComment selectCommentById(Long commentId)
    {
        return userCommentMapper.selectCommentById(commentId);
    }

    @Override
    public List<UserComment> selectCommentList(UserComment userComment)
    {
        return userCommentMapper.selectCommentList(userComment);
    }

    @Override
    public int insertComment(UserComment userComment)
    {
        return userCommentMapper.insertComment(userComment);
    }

    @Override
    public int updateComment(UserComment userComment)
    {
        return userCommentMapper.updateComment(userComment);
    }

    @Override
    public int deleteCommentByIds(String ids)
    {
        return userCommentMapper.deleteCommentByIds(Convert.toStrArray(ids));
    }

    @Override
    public List<UserComment> selectCommentsByArticleId(Long articleId)
    {
        return userCommentMapper.selectCommentsByArticleId(articleId);
    }

    @Override
    public List<UserComment> selectRepliesByParentId(Long parentId)
    {
        return userCommentMapper.selectRepliesByParentId(parentId);
    }

    @Override
    public int incrementLikeCount(Long commentId)
    {
        return userCommentMapper.incrementLikeCount(commentId);
    }

    @Override
    public int incrementReplyCount(Long commentId)
    {
        return userCommentMapper.incrementReplyCount(commentId);
    }
}
