package com.ruoyi.system.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.common.core.text.Convert;
import com.ruoyi.system.domain.Report;
import com.ruoyi.system.mapper.ReportMapper;
import com.ruoyi.system.service.IReportService;

@Service
public class ReportServiceImpl implements IReportService
{
    @Autowired
    private ReportMapper reportMapper;

    @Override
    public Report selectReportById(Long reportId)
    {
        return reportMapper.selectReportById(reportId);
    }

    @Override
    public List<Report> selectReportList(Report report)
    {
        return reportMapper.selectReportList(report);
    }

    @Override
    public int insertReport(Report report)
    {
        return reportMapper.insertReport(report);
    }

    @Override
    public int updateReport(Report report)
    {
        return reportMapper.updateReport(report);
    }

    @Override
    public int deleteReportByIds(String ids)
    {
        return reportMapper.deleteReportByIds(Convert.toStrArray(ids));
    }
}
