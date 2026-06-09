package com.ruoyi.system.mapper;

import java.util.List;
import com.ruoyi.system.domain.UserFavorite;

public interface UserFavoriteMapper
{
    public UserFavorite selectFavoriteById(Long favoriteId);

    public List<UserFavorite> selectFavoriteList(UserFavorite userFavorite);

    public int insertFavorite(UserFavorite userFavorite);

    public int updateFavorite(UserFavorite userFavorite);

    public int deleteFavoriteByIds(String[] favoriteIds);

    public UserFavorite selectFavoriteByUserAndArticle(Long userId, Long articleId);

    public int deleteFavoriteByUserAndArticle(Long userId, Long articleId);

    public List<UserFavorite> selectFavoritesByUserId(Long userId);
}
