package com.ruoyi.system.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.common.core.text.Convert;
import com.ruoyi.system.domain.PointsRecord;
import com.ruoyi.system.mapper.PointsRecordMapper;
import com.ruoyi.system.service.IPointsRecordService;

@Service
public class PointsRecordServiceImpl implements IPointsRecordService
{
    @Autowired
    private PointsRecordMapper pointsRecordMapper;

    @Override
    public PointsRecord selectPointsRecordById(Long recordId)
    {
        return pointsRecordMapper.selectPointsRecordById(recordId);
    }

    @Override
    public List<PointsRecord> selectPointsRecordList(PointsRecord pointsRecord)
    {
        return pointsRecordMapper.selectPointsRecordList(pointsRecord);
    }

    @Override
    public int insertPointsRecord(PointsRecord pointsRecord)
    {
        return pointsRecordMapper.insertPointsRecord(pointsRecord);
    }

    @Override
    public int updatePointsRecord(PointsRecord pointsRecord)
    {
        return pointsRecordMapper.updatePointsRecord(pointsRecord);
    }

    @Override
    public int deletePointsRecordByIds(String ids)
    {
        return pointsRecordMapper.deletePointsRecordByIds(Convert.toStrArray(ids));
    }
}
