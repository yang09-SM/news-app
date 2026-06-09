package com.ruoyi.web.controller.system;

import java.util.List;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
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
import com.ruoyi.system.domain.OfflineNews;
import com.ruoyi.system.service.IOfflineNewsService;

/**
 * 离线新闻 信息操作处理
 *
 * @author ruoyi
 */
@Controller
@RequestMapping("/system/news/offline")
public class OfflineNewsController extends BaseController
{
    private String prefix = "system/news/offline";

    @Autowired
    private IOfflineNewsService offlineNewsService;

    @RequiresPermissions("news:offline:view")
    @GetMapping()
    public String offline()
    {
        return prefix + "/offline";
    }

    /**
     * 查询离线新闻列表
     */
    @RequiresPermissions("news:offline:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(OfflineNews offlineNews)
    {
        startPage();
        List<OfflineNews> list = offlineNewsService.selectOfflineNewsList(offlineNews);
        return getDataTable(list);
    }

    /**
     * 删除离线新闻
     */
    @RequiresPermissions("news:offline:remove")
    @Log(title = "离线新闻", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(offlineNewsService.deleteOfflineNewsByIds(ids));
    }
}
