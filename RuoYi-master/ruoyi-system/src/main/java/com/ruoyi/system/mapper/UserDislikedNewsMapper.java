package com.ruoyi.system.mapper;

import java.util.List;
import com.ruoyi.system.domain.UserDislikedNews;

public interface UserDislikedNewsMapper
{
    public UserDislikedNews selectDislikedNewsById(Long dislikeId);

    public List<UserDislikedNews> selectDislikedNewsList(UserDislikedNews userDislikedNews);

    public int insertDislikedNews(UserDislikedNews userDislikedNews);

    public int updateDislikedNews(UserDislikedNews userDislikedNews);

    public int deleteDislikedNewsByIds(String[] dislikeIds);

    public UserDislikedNews selectLikedNewsByUserAndArticle(Long userId, Long articleId);

    public int deleteLikedNewsByUserAndArticle(Long userId, Long articleId);
}
