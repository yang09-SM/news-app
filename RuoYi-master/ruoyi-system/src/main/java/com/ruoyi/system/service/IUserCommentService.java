package com.ruoyi.system.service;

import java.util.List;
import com.ruoyi.system.domain.UserComment;

public interface IUserCommentService
{
    public UserComment selectCommentById(Long commentId);

    public List<UserComment> selectCommentList(UserComment userComment);

    public int insertComment(UserComment userComment);

    public int updateComment(UserComment userComment);

    public int deleteCommentByIds(String ids);

    public List<UserComment> selectCommentsByArticleId(Long articleId);

    public List<UserComment> selectRepliesByParentId(Long parentId);

    public int incrementLikeCount(Long commentId);

    public int incrementReplyCount(Long commentId);
}
