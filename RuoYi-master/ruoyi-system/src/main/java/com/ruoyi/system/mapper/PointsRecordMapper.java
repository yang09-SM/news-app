package com.ruoyi.system.mapper;

import java.util.List;
import com.ruoyi.system.domain.PointsRecord;

public interface PointsRecordMapper
{
    public PointsRecord selectPointsRecordById(Long recordId);

    public List<PointsRecord> selectPointsRecordList(PointsRecord pointsRecord);

    public int insertPointsRecord(PointsRecord pointsRecord);

    public int updatePointsRecord(PointsRecord pointsRecord);

    public int deletePointsRecordByIds(String[] recordIds);
}
