package com.ruoyi.system.mapper;

import java.util.List;
import com.ruoyi.system.domain.Report;

public interface ReportMapper
{
    public Report selectReportById(Long reportId);

    public List<Report> selectReportList(Report report);

    public int insertReport(Report report);

    public int updateReport(Report report);

    public int deleteReportByIds(String[] reportIds);
}
