package com.ruoyi.web.controller.system;

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
import com.ruoyi.system.domain.Activity;
import com.ruoyi.system.service.IActivityService;

/**
 * 活动管理 信息操作处理
 *
 * @author ruoyi
 */
@Controller
@RequestMapping("/system/news/activity")
public class ActivityController extends BaseController
{
    private String prefix = "system/news/activity";

    @Autowired
    private IActivityService activityService;

    @RequiresPermissions("news:activity:view")
    @GetMapping()
    public String activity()
    {
        return prefix + "/activity";
    }

    /**
     * 查询活动列表
     */
    @RequiresPermissions("news:activity:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(Activity activity)
    {
        startPage();
        List<Activity> list = activityService.selectActivityList(activity);
        return getDataTable(list);
    }

    /**
     * 新增活动
     */
    @RequiresPermissions("news:activity:add")
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }

    /**
     * 新增保存活动
     */
    @RequiresPermissions("news:activity:add")
    @Log(title = "活动管理", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(@Validated Activity activity)
    {
        activity.setCreateBy(getLoginName());
        return toAjax(activityService.insertActivity(activity));
    }

    /**
     * 修改活动
     */
    @RequiresPermissions("news:activity:edit")
    @GetMapping("/edit/{activityId}")
    public String edit(@PathVariable("activityId") Long activityId, ModelMap mmap)
    {
        mmap.put("activity", activityService.selectActivityById(activityId));
        return prefix + "/edit";
    }

    /**
     * 修改保存活动
     */
    @RequiresPermissions("news:activity:edit")
    @Log(title = "活动管理", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(@Validated Activity activity)
    {
        activity.setUpdateBy(getLoginName());
        return toAjax(activityService.updateActivity(activity));
    }

    /**
     * 删除活动
     */
    @RequiresPermissions("news:activity:remove")
    @Log(title = "活动管理", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(activityService.deleteActivityByIds(ids));
    }
}
