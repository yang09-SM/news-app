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
import com.ruoyi.system.domain.Author;
import com.ruoyi.system.service.IAuthorService;

/**
 * 作者管理 信息操作处理
 *
 * @author ruoyi
 */
@Controller
@RequestMapping("/system/news/author")
public class AuthorController extends BaseController
{
    private String prefix = "system/news/author";

    @Autowired
    private IAuthorService authorService;

    @RequiresPermissions("news:author:view")
    @GetMapping()
    public String author()
    {
        return prefix + "/author";
    }

    /**
     * 查询作者列表
     */
    @RequiresPermissions("news:author:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(Author author)
    {
        startPage();
        List<Author> list = authorService.selectAuthorList(author);
        return getDataTable(list);
    }

    /**
     * 新增作者
     */
    @RequiresPermissions("news:author:add")
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }

    /**
     * 新增保存作者
     */
    @RequiresPermissions("news:author:add")
    @Log(title = "作者管理", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(@Validated Author author)
    {
        author.setCreateBy(getLoginName());
        return toAjax(authorService.insertAuthor(author));
    }

    /**
     * 修改作者
     */
    @RequiresPermissions("news:author:edit")
    @GetMapping("/edit/{authorId}")
    public String edit(@PathVariable("authorId") Long authorId, ModelMap mmap)
    {
        mmap.put("author", authorService.selectAuthorById(authorId));
        return prefix + "/edit";
    }

    /**
     * 修改保存作者
     */
    @RequiresPermissions("news:author:edit")
    @Log(title = "作者管理", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(@Validated Author author)
    {
        author.setUpdateBy(getLoginName());
        return toAjax(authorService.updateAuthor(author));
    }

    /**
     * 删除作者
     */
    @RequiresPermissions("news:author:remove")
    @Log(title = "作者管理", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(authorService.deleteAuthorByIds(ids));
    }
}
