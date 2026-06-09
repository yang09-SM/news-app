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
import com.ruoyi.system.domain.UserComment;
import com.ruoyi.system.service.IUserCommentService;

/**
 * 评论管理 信息操作处理
 *
 * @author ruoyi
 */
@Controller
@RequestMapping("/system/news/comment")
public class UserCommentController extends BaseController
{
    private String prefix = "system/news/comment";

    @Autowired
    private IUserCommentService userCommentService;

    @RequiresPermissions("news:comment:view")
    @GetMapping()
    public String comment()
    {
        return prefix + "/comment";
    }

    /**
     * 查询评论列表
     */
    @RequiresPermissions("news:comment:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(UserComment userComment)
    {
        startPage();
        List<UserComment> list = userCommentService.selectCommentList(userComment);
        return getDataTable(list);
    }

    /**
     * 修改评论
     */
    @RequiresPermissions("news:comment:edit")
    @GetMapping("/edit/{commentId}")
    public String edit(@PathVariable("commentId") Long commentId, ModelMap mmap)
    {
        mmap.put("comment", userCommentService.selectCommentById(commentId));
        return prefix + "/edit";
    }

    /**
     * 修改保存评论
     */
    @RequiresPermissions("news:comment:edit")
    @Log(title = "评论管理", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(@Validated UserComment userComment)
    {
        userComment.setUpdateBy(getLoginName());
        return toAjax(userCommentService.updateComment(userComment));
    }

    /**
     * 删除评论
     */
    @RequiresPermissions("news:comment:remove")
    @Log(title = "评论管理", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(userCommentService.deleteCommentByIds(ids));
    }
}
