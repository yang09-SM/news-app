package com.ruoyi.system.mapper;

import java.util.List;
import com.ruoyi.system.domain.ExchangeRecord;

public interface ExchangeRecordMapper
{
    public ExchangeRecord selectExchangeRecordById(Long exchangeId);

    public List<ExchangeRecord> selectExchangeRecordList(ExchangeRecord exchangeRecord);

    public int insertExchangeRecord(ExchangeRecord exchangeRecord);

    public int updateExchangeRecord(ExchangeRecord exchangeRecord);

    public int deleteExchangeRecordByIds(String[] exchangeIds);
}
