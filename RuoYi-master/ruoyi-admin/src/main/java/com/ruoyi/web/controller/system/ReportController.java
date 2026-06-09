package com.ruoyi.web.controller.system;

import java.util.Date;
import java.util.List;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.system.domain.Report;
import com.ruoyi.system.service.IReportService;

/**
 * 举报管理 信息操作处理
 *
 * @author ruoyi
 */
@Controller
@RequestMapping("/system/news/report")
public class ReportController extends BaseController
{
    private String prefix = "system/news/report";

    @Autowired
    private IReportService reportService;

    @RequiresPermissions("news:report:view")
    @GetMapping()
    public String report()
    {
        return prefix + "/report";
    }

    /**
     * 查询举报列表
     */
    @RequiresPermissions("news:report:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(Report report)
    {
        startPage();
        List<Report> list = reportService.selectReportList(report);
        return getDataTable(list);
    }

    /**
     * 处理举报
     */
    @RequiresPermissions("news:report:edit")
    @GetMapping("/edit/{reportId}")
    public String edit(@PathVariable("reportId") Long reportId, ModelMap mmap)
    {
        mmap.put("report", reportService.selectReportById(reportId));
        return prefix + "/handle";
    }

    /**
     * 处理保存举报
     */
    @RequiresPermissions("news:report:edit")
    @Log(title = "举报管理", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(@Validated Report report)
    {
        report.setHandleUserId(getUserId());
        report.setHandleTime(new Date());
        report.setUpdateBy(getLoginName());
        return toAjax(reportService.updateReport(report));
    }

    /**
     * 删除举报
     */
    @RequiresPermissions("news:report:remove")
    @Log(title = "举报管理", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(reportService.deleteReportByIds(ids));
    }
}
