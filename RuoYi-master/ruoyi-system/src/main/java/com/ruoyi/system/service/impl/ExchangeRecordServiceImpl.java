package com.ruoyi.system.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.common.core.text.Convert;
import com.ruoyi.system.domain.ExchangeRecord;
import com.ruoyi.system.mapper.ExchangeRecordMapper;
import com.ruoyi.system.service.IExchangeRecordService;

@Service
public class ExchangeRecordServiceImpl implements IExchangeRecordService
{
    @Autowired
    private ExchangeRecordMapper exchangeRecordMapper;

    @Override
    public ExchangeRecord selectExchangeRecordById(Long exchangeId)
    {
        return exchangeRecordMapper.selectExchangeRecordById(exchangeId);
    }

    @Override
    public List<ExchangeRecord> selectExchangeRecordList(ExchangeRecord exchangeRecord)
    {
        return exchangeRecordMapper.selectExchangeRecordList(exchangeRecord);
    }

    @Override
    public int insertExchangeRecord(ExchangeRecord exchangeRecord)
    {
        return exchangeRecordMapper.insertExchangeRecord(exchangeRecord);
    }

    @Override
    public int updateExchangeRecord(ExchangeRecord exchangeRecord)
    {
        return exchangeRecordMapper.updateExchangeRecord(exchangeRecord);
    }

    @Override
    public int deleteExchangeRecordByIds(String ids)
    {
        return exchangeRecordMapper.deleteExchangeRecordByIds(Convert.toStrArray(ids));
    }
}
