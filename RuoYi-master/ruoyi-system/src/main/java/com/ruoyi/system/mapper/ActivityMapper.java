package com.ruoyi.system.mapper;

import java.util.List;
import com.ruoyi.system.domain.Activity;

public interface ActivityMapper
{
    public Activity selectActivityById(Long activityId);

    public List<Activity> selectActivityList(Activity activity);

    public int insertActivity(Activity activity);

    public int updateActivity(Activity activity);

    public int deleteActivityByIds(String[] activityIds);
}
