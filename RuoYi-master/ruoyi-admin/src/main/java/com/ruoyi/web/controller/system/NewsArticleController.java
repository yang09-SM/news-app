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
import com.ruoyi.system.domain.NewsArticle;
import com.ruoyi.system.service.INewsArticleService;

@Controller
@RequestMapping("/system/news/article")
public class NewsArticleController extends BaseController
{
    private String prefix = "system/news/article";

    @Autowired
    private INewsArticleService newsArticleService;

    @RequiresPermissions("news:article:view")
    @GetMapping()
    public String news()
    {
        return prefix + "/article";
    }

    @RequiresPermissions("news:article:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(NewsArticle newsArticle)
    {
        startPage();
        List<NewsArticle> list = newsArticleService.selectNewsArticleList(newsArticle);
        return getDataTable(list);
    }

    @RequiresPermissions("news:article:add")
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }

    @RequiresPermissions("news:article:add")
    @Log(title = "新闻文章", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(@Validated NewsArticle newsArticle)
    {
        newsArticle.setCreateBy(getLoginName());
        return toAjax(newsArticleService.insertNewsArticle(newsArticle));
    }

    @RequiresPermissions("news:article:edit")
    @GetMapping("/edit/{articleId}")
    public String edit(@PathVariable("articleId") Long articleId, ModelMap mmap)
    {
        mmap.put("newsArticle", newsArticleService.selectNewsArticleById(articleId));
        return prefix + "/edit";
    }

    @RequiresPermissions("news:article:edit")
    @Log(title = "新闻文章", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(@Validated NewsArticle newsArticle)
    {
        newsArticle.setUpdateBy(getLoginName());
        return toAjax(newsArticleService.updateNewsArticle(newsArticle));
    }

    @RequiresPermissions("news:article:view")
    @GetMapping("/view/{articleId}")
    public String view(@PathVariable("articleId") Long articleId, ModelMap mmap)
    {
        mmap.put("newsArticle", newsArticleService.selectNewsArticleById(articleId));
        return prefix + "/detail";
    }

    @RequiresPermissions("news:article:remove")
    @Log(title = "新闻文章", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(newsArticleService.deleteNewsArticleByIds(ids));
    }
}
