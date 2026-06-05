package com.ruoyi.web.controller.api;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.ruoyi.common.annotation.Anonymous;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.system.domain.NewsArticle;
import com.ruoyi.system.service.INewsArticleService;

@Controller
@RequestMapping("/api/news")
@Anonymous
public class NewsApiController extends BaseController
{
    @Autowired
    private INewsArticleService newsArticleService;

    @GetMapping("/list")
    @ResponseBody
    public TableDataInfo list(@RequestParam(value = "categoryId", required = false) Long categoryId)
    {
        startPage();
        NewsArticle newsArticle = new NewsArticle();
        if (categoryId != null)
        {
            newsArticle.setCategoryId(categoryId);
        }
        List<NewsArticle> list = newsArticleService.selectPublishedNewsList(newsArticle);
        return getDataTable(list);
    }

    @GetMapping("/{articleId}")
    @ResponseBody
    public AjaxResult getInfo(@PathVariable("articleId") Long articleId)
    {
        NewsArticle newsArticle = newsArticleService.selectNewsArticleDetail(articleId);
        if (newsArticle == null)
        {
            return AjaxResult.error("新闻不存在");
        }
        return AjaxResult.success(newsArticle);
    }
}
