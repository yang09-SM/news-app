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
import com.ruoyi.system.domain.Channel;
import com.ruoyi.system.service.IChannelService;

/**
 * 频道管理 信息操作处理
 *
 * @author ruoyi
 */
@Controller
@RequestMapping("/system/news/channel")
public class ChannelController extends BaseController
{
    private String prefix = "system/news/channel";

    @Autowired
    private IChannelService channelService;

    @RequiresPermissions("news:channel:view")
    @GetMapping()
    public String channel()
    {
        return prefix + "/channel";
    }

    /**
     * 查询频道列表
     */
    @RequiresPermissions("news:channel:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(Channel channel)
    {
        startPage();
        List<Channel> list = channelService.selectChannelList(channel);
        return getDataTable(list);
    }

    /**
     * 新增频道
     */
    @RequiresPermissions("news:channel:add")
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }

    /**
     * 新增保存频道
     */
    @RequiresPermissions("news:channel:add")
    @Log(title = "频道管理", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(@Validated Channel channel)
    {
        channel.setCreateBy(getLoginName());
        return toAjax(channelService.insertChannel(channel));
    }

    /**
     * 修改频道
     */
    @RequiresPermissions("news:channel:edit")
    @GetMapping("/edit/{channelId}")
    public String edit(@PathVariable("channelId") Long channelId, ModelMap mmap)
    {
        mmap.put("channel", channelService.selectChannelById(channelId));
        return prefix + "/edit";
    }

    /**
     * 修改保存频道
     */
    @RequiresPermissions("news:channel:edit")
    @Log(title = "频道管理", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(@Validated Channel channel)
    {
        channel.setUpdateBy(getLoginName());
        return toAjax(channelService.updateChannel(channel));
    }

    /**
     * 删除频道
     */
    @RequiresPermissions("news:channel:remove")
    @Log(title = "频道管理", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(channelService.deleteChannelByIds(ids));
    }
}
