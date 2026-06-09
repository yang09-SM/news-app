package com.ruoyi.web.controller.api;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.ruoyi.common.annotation.Anonymous;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.system.domain.NewsCategory;
import com.ruoyi.system.service.INewsCategoryService;

@Controller
@RequestMapping("/api/category")
@Anonymous
public class CategoryApiController extends BaseController
{
    @Autowired
    private INewsCategoryService categoryService;

    @GetMapping("/list")
    @ResponseBody
    public AjaxResult list()
    {
        List<NewsCategory> list = categoryService.selectEnabledCategoryList();
        return AjaxResult.success(list);
    }

    @GetMapping("/all")
    @ResponseBody
    public AjaxResult getAll()
    {
        NewsCategory newsCategory = new NewsCategory();
        List<NewsCategory> list = categoryService.selectCategoryList(newsCategory);
        return AjaxResult.success(list);
    }

    @GetMapping("/{categoryId}")
    @ResponseBody
    public AjaxResult getInfo(@PathVariable("categoryId") Long categoryId)
    {
        NewsCategory newsCategory = categoryService.selectCategoryById(categoryId);
        if (newsCategory == null)
        {
            return AjaxResult.error("分类不存在");
        }
        return AjaxResult.success(newsCategory);
    }
}
