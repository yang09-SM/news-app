package com.ruoyi.system.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.common.core.text.Convert;
import com.ruoyi.system.domain.Activity;
import com.ruoyi.system.mapper.ActivityMapper;
import com.ruoyi.system.service.IActivityService;

@Service
public class ActivityServiceImpl implements IActivityService
{
    @Autowired
    private ActivityMapper activityMapper;

    @Override
    public Activity selectActivityById(Long activityId)
    {
        return activityMapper.selectActivityById(activityId);
    }

    @Override
    public List<Activity> selectActivityList(Activity activity)
    {
        return activityMapper.selectActivityList(activity);
    }

    @Override
    public int insertActivity(Activity activity)
    {
        return activityMapper.insertActivity(activity);
    }

    @Override
    public int updateActivity(Activity activity)
    {
        return activityMapper.updateActivity(activity);
    }

    @Override
    public int deleteActivityByIds(String ids)
    {
        return activityMapper.deleteActivityByIds(Convert.toStrArray(ids));
    }
}
