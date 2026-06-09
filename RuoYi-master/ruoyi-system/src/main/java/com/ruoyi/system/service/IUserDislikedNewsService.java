package com.ruoyi.system.service;

import java.util.List;
import com.ruoyi.system.domain.UserDislikedNews;

public interface IUserDislikedNewsService
{
    public UserDislikedNews selectDislikedNewsById(Long dislikeId);

    public List<UserDislikedNews> selectDislikedNewsList(UserDislikedNews userDislikedNews);

    public int insertDislikedNews(UserDislikedNews userDislikedNews);

    public int updateDislikedNews(UserDislikedNews userDislikedNews);

    public int deleteDislikedNewsByIds(String ids);

    public UserDislikedNews selectLikedNewsByUserAndArticle(Long userId, Long articleId);

    public int deleteLikedNewsByUserAndArticle(Long userId, Long articleId);
}
