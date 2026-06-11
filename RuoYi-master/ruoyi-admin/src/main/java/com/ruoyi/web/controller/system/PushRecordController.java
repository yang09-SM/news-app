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
import com.ruoyi.common.core.push.JPushService;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.system.domain.PushRecord;
import com.ruoyi.system.service.IPushRecordService;

/**
 * 推送管理 信息操作处理
 *
 * @author ruoyi
 */
@Controller
@RequestMapping("/system/news/push")
public class PushRecordController extends BaseController
{
    private String prefix = "system/news/push";

    @Autowired
    private IPushRecordService pushRecordService;

    @Autowired
    private JPushService jPushService;

    @RequiresPermissions("news:push:view")
    @GetMapping()
    public String push()
    {
        return prefix + "/push";
    }

    /**
     * 查询推送记录列表
     */
    @RequiresPermissions("news:push:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(PushRecord pushRecord)
    {
        startPage();
        List<PushRecord> list = pushRecordService.selectPushRecordList(pushRecord);
        return getDataTable(list);
    }

    /**
     * 新增推送任务
     */
    @RequiresPermissions("news:push:add")
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }

    /**
     * 新增保存推送任务
     */
    @RequiresPermissions("news:push:add")
    @Log(title = "推送管理", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(@Validated PushRecord pushRecord)
    {
        pushRecord.setCreateBy(getLoginName());
        pushRecord.setSendStatus("0"); // 待发送状态
        return toAjax(pushRecordService.insertPushRecord(pushRecord));
    }

    /**
     * 修改推送任务
     */
    @RequiresPermissions("news:push:edit")
    @GetMapping("/edit/{pushId}")
    public String edit(@PathVariable("pushId") Long pushId, ModelMap mmap)
    {
        mmap.put("pushRecord", pushRecordService.selectPushRecordById(pushId));
        return prefix + "/edit";
    }

    /**
     * 修改保存推送任务
     */
    @RequiresPermissions("news:push:edit")
    @Log(title = "推送管理", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(@Validated PushRecord pushRecord)
    {
        pushRecord.setUpdateBy(getLoginName());
        return toAjax(pushRecordService.updatePushRecord(pushRecord));
    }

    /**
     * 删除推送记录（逻辑删除）
     */
    @RequiresPermissions("news:push:remove")
    @Log(title = "推送管理", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(pushRecordService.deletePushRecordByIds(ids));
    }

    /**
     * 手动发送推送
     */
    @RequiresPermissions("news:push:edit")
    @Log(title = "推送管理", businessType = BusinessType.UPDATE)
    @PostMapping("/send/{pushId}")
    @ResponseBody
    public AjaxResult sendPush(@PathVariable("pushId") Long pushId)
    {
        PushRecord pushRecord = pushRecordService.selectPushRecordById(pushId);
        if (pushRecord == null)
        {
            return error("推送记录不存在");
        }
        if (!"0".equals(pushRecord.getSendStatus()))
        {
            return error("该推送已发送或已失败，无法重复发送");
        }
        try
        {
            String pushType = pushRecord.getPushType();
            switch (pushType)
            {
                case "all":
                    jPushService.pushToAll(pushRecord.getTitle(), pushRecord.getContent());
                    break;
                case "alias":
                    jPushService.pushByAlias(pushRecord.getTargetValue(), pushRecord.getTitle(), pushRecord.getContent());
                    break;
                case "tag":
                    jPushService.pushByTag(pushRecord.getTargetValue(), pushRecord.getTitle(), pushRecord.getContent());
                    break;
                case "single":
                    jPushService.pushToSingle(pushRecord.getTargetValue(), pushRecord.getTitle(), pushRecord.getContent());
                    break;
                default:
                    return error("不支持的推送类型: " + pushType);
            }
            // 更新发送状态和时间
            pushRecord.setSendStatus("1"); // 已发送
            pushRecord.setSendTime(new Date());
            pushRecord.setUpdateBy(getLoginName());
            pushRecordService.updatePushRecord(pushRecord);
            return success("推送发送成功");
        }
        catch (Exception e)
        {
            // 更新为发送失败状态
            pushRecord.setSendStatus("2"); // 发送失败
            pushRecordService.updatePushRecord(pushRecord);
            return error("推送发送失败: " + e.getMessage());
        }
    }
}
