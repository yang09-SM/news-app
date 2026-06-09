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
import com.ruoyi.system.domain.Topic;
import com.ruoyi.system.service.ITopicService;

/**
 * 主题管理
 * 
 * @author ruoyi
 */
@Controller
@RequestMapping("/system/news/topic")
public class TopicController extends BaseController
{
    private String prefix = "system/news/topic";

    @Autowired
    private ITopicService topicService;

    @RequiresPermissions("news:topic:view")
    @GetMapping()
    public String topic()
    {
        return prefix + "/topic";
    }

    /**
     * 查询主题列表
     */
    @RequiresPermissions("news:topic:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(Topic topic)
    {
        startPage();
        List<Topic> list = topicService.selectTopicList(topic);
        return getDataTable(list);
    }

    /**
     * 新增主题
     */
    @RequiresPermissions("news:topic:add")
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }

    /**
     * 新增保存主题
     */
    @RequiresPermissions("news:topic:add")
    @Log(title = "主题管理", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(@Validated Topic topic)
    {
        topic.setCreateBy(getLoginName());
        return toAjax(topicService.insertTopic(topic));
    }

    /**
     * 修改主题
     */
    @RequiresPermissions("news:topic:edit")
    @GetMapping("/edit/{topicId}")
    public String edit(@PathVariable("topicId") Long topicId, ModelMap mmap)
    {
        mmap.put("topic", topicService.selectTopicById(topicId));
        return prefix + "/edit";
    }

    /**
     * 修改保存主题
     */
    @RequiresPermissions("news:topic:edit")
    @Log(title = "主题管理", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(@Validated Topic topic)
    {
        topic.setUpdateBy(getLoginName());
        return toAjax(topicService.updateTopic(topic));
    }

    /**
     * 删除主题
     */
    @RequiresPermissions("news:topic:remove")
    @Log(title = "主题管理", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(topicService.deleteTopicByIds(ids));
    }
}
