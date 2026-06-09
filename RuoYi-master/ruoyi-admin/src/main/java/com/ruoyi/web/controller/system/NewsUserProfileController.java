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
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.system.domain.UserProfile;
import com.ruoyi.system.service.IUserProfileService;

/**
 * 用户管理 信息操作处理
 *
 * @author ruoyi
 */
@Controller
@RequestMapping("/system/news/user")
public class NewsUserProfileController extends BaseController
{
    private String prefix = "system/news/user";

    @Autowired
    private IUserProfileService userProfileService;

    @RequiresPermissions("news:user:view")
    @GetMapping()
    public String user()
    {
        return prefix + "/user";
    }

    /**
     * 查询用户列表
     */
    @RequiresPermissions("news:user:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(UserProfile userProfile)
    {
        startPage();
        List<UserProfile> list = userProfileService.selectUserProfileList(userProfile);
        return getDataTable(list);
    }

    /**
     * 用户详情
     */
    @RequiresPermissions("news:user:view")
    @GetMapping("/detail/{profileId}")
    public String detail(@PathVariable("profileId") Long profileId, ModelMap mmap)
    {
        mmap.put("profile", userProfileService.selectUserProfileById(profileId));
        return prefix + "/detail";
    }
}
