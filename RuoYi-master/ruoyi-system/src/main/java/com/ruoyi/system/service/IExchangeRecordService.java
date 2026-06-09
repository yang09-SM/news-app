package com.ruoyi.system.service;

import java.util.List;
import com.ruoyi.system.domain.ExchangeRecord;

public interface IExchangeRecordService
{
    public ExchangeRecord selectExchangeRecordById(Long exchangeId);

    public List<ExchangeRecord> selectExchangeRecordList(ExchangeRecord exchangeRecord);

    public int insertExchangeRecord(ExchangeRecord exchangeRecord);

    public int updateExchangeRecord(ExchangeRecord exchangeRecord);

    public int deleteExchangeRecordByIds(String ids);
}
