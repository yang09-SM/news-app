package com.ruoyi.system.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.common.core.text.Convert;
import com.ruoyi.system.domain.PushRecord;
import com.ruoyi.system.mapper.PushRecordMapper;
import com.ruoyi.system.service.IPushRecordService;

/**
 * 推送记录Service业务层处理
 *
 * @author ruoyi
 */
@Service
public class PushRecordServiceImpl implements IPushRecordService
{
    @Autowired
    private PushRecordMapper pushRecordMapper;

    @Override
    public PushRecord selectPushRecordById(Long pushId)
    {
        return pushRecordMapper.selectPushRecordById(pushId);
    }

    @Override
    public List<PushRecord> selectPushRecordList(PushRecord pushRecord)
    {
        return pushRecordMapper.selectPushRecordList(pushRecord);
    }

    @Override
    public List<PushRecord> selectPendingPushes()
    {
        return pushRecordMapper.selectPendingPushes();
    }

    @Override
    public int insertPushRecord(PushRecord pushRecord)
    {
        return pushRecordMapper.insertPushRecord(pushRecord);
    }

    @Override
    public int updatePushRecord(PushRecord pushRecord)
    {
        return pushRecordMapper.updatePushRecord(pushRecord);
    }

    @Override
    public int deletePushRecordByIds(String ids)
    {
        return pushRecordMapper.deletePushRecordByIds(Convert.toLongArray(ids));
    }
}
