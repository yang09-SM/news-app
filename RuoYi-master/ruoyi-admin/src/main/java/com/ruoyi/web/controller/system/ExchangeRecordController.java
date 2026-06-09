package com.ruoyi.web.controller.system;

import java.util.List;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.system.domain.ExchangeRecord;
import com.ruoyi.system.service.IExchangeRecordService;

/**
 * 兑换记录 信息操作处理
 *
 * @author ruoyi
 */
@Controller
@RequestMapping("/system/news/exchange")
public class ExchangeRecordController extends BaseController
{
    private String prefix = "system/news/exchange";

    @Autowired
    private IExchangeRecordService exchangeRecordService;

    @RequiresPermissions("news:exchange:view")
    @GetMapping()
    public String exchange()
    {
        return prefix + "/exchange";
    }

    /**
     * 查询兑换记录列表
     */
    @RequiresPermissions("news:exchange:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(ExchangeRecord exchangeRecord)
    {
        startPage();
        List<ExchangeRecord> list = exchangeRecordService.selectExchangeRecordList(exchangeRecord);
        return getDataTable(list);
    }

    /**
     * 删除兑换记录
     */
    @RequiresPermissions("news:exchange:remove")
    @Log(title = "兑换记录", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(exchangeRecordService.deleteExchangeRecordByIds(ids));
    }
}
