package com.ruoyi.system.service;

import java.util.List;
import com.ruoyi.system.domain.Report;

public interface IReportService
{
    public Report selectReportById(Long reportId);

    public List<Report> selectReportList(Report report);

    public int insertReport(Report report);

    public int updateReport(Report report);

    public int deleteReportByIds(String ids);
}
