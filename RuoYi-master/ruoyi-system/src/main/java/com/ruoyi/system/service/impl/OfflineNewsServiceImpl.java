package com.ruoyi.system.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.common.core.text.Convert;
import com.ruoyi.system.domain.OfflineNews;
import com.ruoyi.system.mapper.OfflineNewsMapper;
import com.ruoyi.system.service.IOfflineNewsService;

@Service
public class OfflineNewsServiceImpl implements IOfflineNewsService
{
    @Autowired
    private OfflineNewsMapper offlineNewsMapper;

    @Override
    public OfflineNews selectOfflineNewsById(Long offlineId)
    {
        return offlineNewsMapper.selectOfflineNewsById(offlineId);
    }

    @Override
    public List<OfflineNews> selectOfflineNewsList(OfflineNews offlineNews)
    {
        return offlineNewsMapper.selectOfflineNewsList(offlineNews);
    }

    @Override
    public int insertOfflineNews(OfflineNews offlineNews)
    {
        return offlineNewsMapper.insertOfflineNews(offlineNews);
    }

    @Override
    public int updateOfflineNews(OfflineNews offlineNews)
    {
        return offlineNewsMapper.updateOfflineNews(offlineNews);
    }

    @Override
    public int deleteOfflineNewsByIds(String ids)
    {
        return offlineNewsMapper.deleteOfflineNewsByIds(Convert.toStrArray(ids));
    }
}
