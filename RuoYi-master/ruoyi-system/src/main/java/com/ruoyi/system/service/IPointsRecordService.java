package com.ruoyi.system.service;

import java.util.List;
import com.ruoyi.system.domain.PointsRecord;

public interface IPointsRecordService
{
    public PointsRecord selectPointsRecordById(Long recordId);

    public List<PointsRecord> selectPointsRecordList(PointsRecord pointsRecord);

    public int insertPointsRecord(PointsRecord pointsRecord);

    public int updatePointsRecord(PointsRecord pointsRecord);

    public int deletePointsRecordByIds(String ids);
}
