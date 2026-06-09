package com.ruoyi.system.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.common.core.text.Convert;
import com.ruoyi.system.domain.UserFavorite;
import com.ruoyi.system.mapper.UserFavoriteMapper;
import com.ruoyi.system.service.IUserFavoriteService;

@Service
public class UserFavoriteServiceImpl implements IUserFavoriteService
{
    @Autowired
    private UserFavoriteMapper userFavoriteMapper;

    @Override
    public UserFavorite selectFavoriteById(Long favoriteId)
    {
        return userFavoriteMapper.selectFavoriteById(favoriteId);
    }

    @Override
    public List<UserFavorite> selectFavoriteList(UserFavorite userFavorite)
    {
        return userFavoriteMapper.selectFavoriteList(userFavorite);
    }

    @Override
    public int insertFavorite(UserFavorite userFavorite)
    {
        return userFavoriteMapper.insertFavorite(userFavorite);
    }

    @Override
    public int updateFavorite(UserFavorite userFavorite)
    {
        return userFavoriteMapper.updateFavorite(userFavorite);
    }

    @Override
    public int deleteFavoriteByIds(String ids)
    {
        return userFavoriteMapper.deleteFavoriteByIds(Convert.toStrArray(ids));
    }

    @Override
    public UserFavorite selectFavoriteByUserAndArticle(Long userId, Long articleId)
    {
        return userFavoriteMapper.selectFavoriteByUserAndArticle(userId, articleId);
    }

    @Override
    public int deleteFavoriteByUserAndArticle(Long userId, Long articleId)
    {
        return userFavoriteMapper.deleteFavoriteByUserAndArticle(userId, articleId);
    }

    @Override
    public List<UserFavorite> selectFavoritesByUserId(Long userId)
    {
        return userFavoriteMapper.selectFavoritesByUserId(userId);
    }
}
