package com.ruoyi.web.controller.system;

import java.util.List;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.system.domain.PointsRecord;
import com.ruoyi.system.service.IPointsRecordService;

/**
 * 积分记录 信息操作处理
 *
 * @author ruoyi
 */
@Controller
@RequestMapping("/system/news/points")
public class PointsRecordController extends BaseController
{
    private String prefix = "system/news/points";

    @Autowired
    private IPointsRecordService pointsRecordService;

    @RequiresPermissions("news:points:view")
    @GetMapping()
    public String points()
    {
        return prefix + "/points";
    }

    /**
     * 查询积分记录列表
     */
    @RequiresPermissions("news:points:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(PointsRecord pointsRecord)
    {
        startPage();
        List<PointsRecord> list = pointsRecordService.selectPointsRecordList(pointsRecord);
        return getDataTable(list);
    }

    /**
     * 删除积分记录
     */
    @RequiresPermissions("news:points:remove")
    @Log(title = "积分记录", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(pointsRecordService.deletePointsRecordByIds(ids));
    }
}
