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
import com.ruoyi.system.domain.UserMessage;
import com.ruoyi.system.service.IUserMessageService;

/**
 * 消息管理 信息操作处理
 *
 * @author ruoyi
 */
@Controller
@RequestMapping("/system/news/message")
public class UserMessageController extends BaseController
{
    private String prefix = "system/news/message";

    @Autowired
    private IUserMessageService messageService;

    @RequiresPermissions("news:message:view")
    @GetMapping()
    public String message()
    {
        return prefix + "/message";
    }

    /**
     * 查询消息列表
     */
    @RequiresPermissions("news:message:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(UserMessage userMessage)
    {
        startPage();
        List<UserMessage> list = messageService.selectMessageList(userMessage);
        return getDataTable(list);
    }

    /**
     * 新增消息
     */
    @RequiresPermissions("news:message:add")
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }

    /**
     * 新增保存消息
     */
    @RequiresPermissions("news:message:add")
    @Log(title = "消息管理", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(@Validated UserMessage userMessage)
    {
        userMessage.setCreateBy(getLoginName());
        return toAjax(messageService.insertMessage(userMessage));
    }

    /**
     * 修改消息
     */
    @RequiresPermissions("news:message:edit")
    @GetMapping("/edit/{messageId}")
    public String edit(@PathVariable("messageId") Long messageId, ModelMap mmap)
    {
        mmap.put("userMessage", messageService.selectMessageById(messageId));
        return prefix + "/edit";
    }

    /**
     * 修改保存消息
     */
    @RequiresPermissions("news:message:edit")
    @Log(title = "消息管理", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(@Validated UserMessage userMessage)
    {
        userMessage.setUpdateBy(getLoginName());
        return toAjax(messageService.updateMessage(userMessage));
    }

    /**
     * 删除消息
     */
    @RequiresPermissions("news:message:remove")
    @Log(title = "消息管理", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(messageService.deleteMessageByIds(ids));
    }
}
