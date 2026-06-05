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
import com.ruoyi.system.domain.NewsCategory;
import com.ruoyi.system.service.INewsCategoryService;

/**
 * 新闻分类 信息操作处理
 * 
 * @author ruoyi
 */
@Controller
@RequestMapping("/system/news/category")
public class NewsCategoryController extends BaseController
{
    private String prefix = "system/news/category";

    @Autowired
    private INewsCategoryService categoryService;

    @RequiresPermissions("news:category:view")
    @GetMapping()
    public String category()
    {
        return prefix + "/category";
    }

    /**
     * 查询新闻分类列表
     */
    @RequiresPermissions("news:category:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(NewsCategory newsCategory)
    {
        startPage();
        List<NewsCategory> list = categoryService.selectCategoryList(newsCategory);
        return getDataTable(list);
    }

    /**
     * 新增新闻分类
     */
    @RequiresPermissions("news:category:add")
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }

    /**
     * 新增保存新闻分类
     */
    @RequiresPermissions("news:category:add")
    @Log(title = "新闻分类", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(@Validated NewsCategory newsCategory)
    {
        newsCategory.setCreateBy(getLoginName());
        return toAjax(categoryService.insertCategory(newsCategory));
    }

    /**
     * 修改新闻分类
     */
    @RequiresPermissions("news:category:edit")
    @GetMapping("/edit/{categoryId}")
    public String edit(@PathVariable("categoryId") Long categoryId, ModelMap mmap)
    {
        mmap.put("category", categoryService.selectCategoryById(categoryId));
        return prefix + "/edit";
    }

    /**
     * 修改保存新闻分类
     */
    @RequiresPermissions("news:category:edit")
    @Log(title = "新闻分类", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(@Validated NewsCategory newsCategory)
    {
        newsCategory.setUpdateBy(getLoginName());
        return toAjax(categoryService.updateCategory(newsCategory));
    }

    /**
     * 删除新闻分类
     */
    @RequiresPermissions("news:category:remove")
    @Log(title = "新闻分类", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(categoryService.deleteCategoryByIds(ids));
    }
}
