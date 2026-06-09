package com.ruoyi.system.mapper;

import java.util.List;
import com.ruoyi.system.domain.OfflineNews;

public interface OfflineNewsMapper
{
    public OfflineNews selectOfflineNewsById(Long offlineId);

    public List<OfflineNews> selectOfflineNewsList(OfflineNews offlineNews);

    public int insertOfflineNews(OfflineNews offlineNews);

    public int updateOfflineNews(OfflineNews offlineNews);

    public int deleteOfflineNewsByIds(String[] offlineIds);
}
