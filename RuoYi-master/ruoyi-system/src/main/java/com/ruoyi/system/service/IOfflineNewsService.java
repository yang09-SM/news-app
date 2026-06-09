package com.ruoyi.system.service;

import java.util.List;
import com.ruoyi.system.domain.OfflineNews;

public interface IOfflineNewsService
{
    public OfflineNews selectOfflineNewsById(Long offlineId);

    public List<OfflineNews> selectOfflineNewsList(OfflineNews offlineNews);

    public int insertOfflineNews(OfflineNews offlineNews);

    public int updateOfflineNews(OfflineNews offlineNews);

    public int deleteOfflineNewsByIds(String ids);
}
