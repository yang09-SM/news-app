package com.ruoyi.web.controller.api;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
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
}
