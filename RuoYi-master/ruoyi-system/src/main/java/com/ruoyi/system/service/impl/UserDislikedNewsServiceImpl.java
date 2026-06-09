package com.ruoyi.system.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.common.core.text.Convert;
import com.ruoyi.system.domain.UserDislikedNews;
import com.ruoyi.system.mapper.UserDislikedNewsMapper;
import com.ruoyi.system.service.IUserDislikedNewsService;

@Service
public class UserDislikedNewsServiceImpl implements IUserDislikedNewsService
{
    @Autowired
    private UserDislikedNewsMapper userDislikedNewsMapper;

    @Override
    public UserDislikedNews selectDislikedNewsById(Long dislikeId)
    {
        return userDislikedNewsMapper.selectDislikedNewsById(dislikeId);
    }

    @Override
    public List<UserDislikedNews> selectDislikedNewsList(UserDislikedNews userDislikedNews)
    {
        return userDislikedNewsMapper.selectDislikedNewsList(userDislikedNews);
    }

    @Override
    public int insertDislikedNews(UserDislikedNews userDislikedNews)
    {
        return userDislikedNewsMapper.insertDislikedNews(userDislikedNews);
    }

    @Override
    public int updateDislikedNews(UserDislikedNews userDislikedNews)
    {
        return userDislikedNewsMapper.updateDislikedNews(userDislikedNews);
    }

    @Override
    public int deleteDislikedNewsByIds(String ids)
    {
        return userDislikedNewsMapper.deleteDislikedNewsByIds(Convert.toStrArray(ids));
    }

    @Override
    public UserDislikedNews selectLikedNewsByUserAndArticle(Long userId, Long articleId)
    {
        return userDislikedNewsMapper.selectLikedNewsByUserAndArticle(userId, articleId);
    }

    @Override
    public int deleteLikedNewsByUserAndArticle(Long userId, Long articleId)
    {
        return userDislikedNewsMapper.deleteLikedNewsByUserAndArticle(userId, articleId);
    }
}
