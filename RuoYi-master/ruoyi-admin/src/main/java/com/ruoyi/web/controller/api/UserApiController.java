package com.ruoyi.web.controller.api;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.ruoyi.common.annotation.Anonymous;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.system.domain.UserBrowsingHistory;
import com.ruoyi.system.domain.UserFollow;
import com.ruoyi.system.domain.UserInterest;
import com.ruoyi.system.domain.UserProfile;
import com.ruoyi.system.service.IUserBrowsingHistoryService;
import com.ruoyi.system.service.IUserFollowService;
import com.ruoyi.system.service.IUserInterestService;
import com.ruoyi.system.service.IUserProfileService;

/**
 * 用户相关API控制器
 */
@Controller
@RequestMapping("/api/user")
@Anonymous
public class UserApiController extends BaseController
{
    @Autowired
    private IUserProfileService userProfileService;

    @Autowired
    private IUserInterestService userInterestService;

    @Autowired
    private IUserFollowService userFollowService;

    @Autowired
    private IUserBrowsingHistoryService browsingHistoryService;

    // ==================== 用户画像 ====================

    @GetMapping("/profile/{userId}")
    @ResponseBody
    public AjaxResult getProfile(@PathVariable("userId") Long userId)
    {
        UserProfile profile = userProfileService.selectUserProfileByUserId(userId);
        if (profile == null)
        {
            return AjaxResult.error("用户资料不存在");
        }
        return AjaxResult.success(profile);
    }

    @PutMapping("/profile")
    @ResponseBody
    public AjaxResult updateProfile(@RequestBody Map<String, Object> params)
    {
        Long userId = Long.valueOf(params.get("userId").toString());
        UserProfile profile = userProfileService.selectUserProfileByUserId(userId);
        if (profile == null)
        {
            return AjaxResult.error("用户资料不存在");
        }

        if (params.containsKey("nickname"))
        {
            profile.setNickname(params.get("nickname").toString());
        }
        if (params.containsKey("avatar"))
        {
            profile.setAvatar(params.get("avatar").toString());
        }
        if (params.containsKey("bio"))
        {
            profile.setBio(params.get("bio").toString());
        }
        profile.setUpdateBy("api");

        int result = userProfileService.updateUserProfile(profile);
        if (result > 0)
        {
            return AjaxResult.success(profile);
        }
        return AjaxResult.error("更新失败");
    }

    // ==================== 兴趣标签 ====================

    @GetMapping("/interests/{userId}")
    @ResponseBody
    public AjaxResult getInterests(@PathVariable("userId") Long userId)
    {
        UserInterest query = new UserInterest();
        query.setUserId(userId);
        List<UserInterest> interests = userInterestService.selectInterestList(query);
        return AjaxResult.success(interests);
    }

    @PostMapping("/interests/{userId}")
    @ResponseBody
    public AjaxResult updateInterests(@PathVariable("userId") Long userId, @RequestBody Map<String, Object> params)
    {
        @SuppressWarnings("unchecked")
        List<String> tags = (List<String>) params.get("tags");
        if (tags == null || tags.isEmpty())
        {
            return AjaxResult.error("标签列表不能为空");
        }

        // 删除旧标签
        UserInterest query = new UserInterest();
        query.setUserId(userId);
        List<UserInterest> existing = userInterestService.selectInterestList(query);
        for (UserInterest interest : existing)
        {
            userInterestService.deleteInterestById(interest.getInterestId());
        }

        // 添加新标签
        for (String tag : tags)
        {
            UserInterest interest = new UserInterest();
            interest.setUserId(userId);
            interest.setTagName(tag);
            interest.setWeight(1);
            interest.setCreateBy("api");
            userInterestService.insertInterest(interest);
        }

        return AjaxResult.success("更新成功");
    }

    @PostMapping("/interests/{userId}/auto-update")
    @ResponseBody
    public AjaxResult autoUpdateInterests(@PathVariable("userId") Long userId)
    {
        // 根据浏览历史自动计算兴趣标签
        UserBrowsingHistory historyQuery = new UserBrowsingHistory();
        historyQuery.setUserId(userId);
        List<UserBrowsingHistory> recentHistory = browsingHistoryService.selectBrowsingHistoryList(historyQuery);

        // 删除旧的自动生成标签
        UserInterest query = new UserInterest();
        query.setUserId(userId);
        List<UserInterest> existing = userInterestService.selectInterestList(query);
        for (UserInterest interest : existing)
        {
            userInterestService.deleteInterestById(interest.getInterestId());
        }

        // 统计文章浏览频次，生成兴趣标签
        Map<Long, Integer> articleCount = new HashMap<>();
        for (UserBrowsingHistory history : recentHistory)
        {
            articleCount.merge(history.getArticleId(), 1, Integer::sum);
        }

        int totalViews = recentHistory.size();
        if (totalViews > 0)
        {
            // 取浏览频次最高的文章作为兴趣
            articleCount.entrySet().stream()
                .sorted(Map.Entry.<Long, Integer>comparingByValue().reversed())
                .limit(10)
                .forEach(entry -> {
                    UserInterest interest = new UserInterest();
                    interest.setUserId(userId);
                    interest.setTagName("article_" + entry.getKey());
                    interest.setWeight(entry.getValue() * 100 / totalViews);
                    interest.setCreateBy("api");
                    userInterestService.insertInterest(interest);
                });
        }

        return AjaxResult.success("兴趣标签已自动更新");
    }

    // ==================== 关注关系 ====================

    @PostMapping("/follow")
    @ResponseBody
    public AjaxResult followUser(@RequestBody Map<String, Object> params)
    {
        Long userId = Long.valueOf(params.get("userId").toString());
        Long followUserId = Long.valueOf(params.get("followUserId").toString());

        if (userId.equals(followUserId))
        {
            return AjaxResult.error("不能关注自己");
        }

        UserFollow existing = userFollowService.selectFollowByUserAndTarget(userId, followUserId);
        if (existing != null)
        {
            return AjaxResult.error("已关注该用户");
        }

        UserFollow userFollow = new UserFollow();
        userFollow.setUserId(userId);
        userFollow.setFollowUserId(followUserId);
        userFollow.setFollowTime(new Date());
        userFollow.setCreateBy("api");

        int result = userFollowService.insertFollow(userFollow);
        if (result > 0)
        {
            // 更新双方计数
            UserProfile userProfile = userProfileService.selectUserProfileByUserId(userId);
            if (userProfile != null)
            {
                userProfile.setFollowingCount(userProfile.getFollowingCount() + 1);
                userProfileService.updateUserProfile(userProfile);
            }
            UserProfile targetProfile = userProfileService.selectUserProfileByUserId(followUserId);
            if (targetProfile != null)
            {
                targetProfile.setFollowersCount(targetProfile.getFollowersCount() + 1);
                userProfileService.updateUserProfile(targetProfile);
            }
            return AjaxResult.success("关注成功");
        }
        return AjaxResult.error("关注失败");
    }

    @DeleteMapping("/follow")
    @ResponseBody
    public AjaxResult unfollowUser(@RequestBody Map<String, Object> params)
    {
        Long userId = Long.valueOf(params.get("userId").toString());
        Long followUserId = Long.valueOf(params.get("followUserId").toString());

        UserFollow existing = userFollowService.selectFollowByUserAndTarget(userId, followUserId);
        if (existing == null)
        {
            return AjaxResult.error("未关注该用户");
        }

        int result = userFollowService.deleteFollowById(existing.getFollowId());
        if (result > 0)
        {
            UserProfile userProfile = userProfileService.selectUserProfileByUserId(userId);
            if (userProfile != null && userProfile.getFollowingCount() > 0)
            {
                userProfile.setFollowingCount(userProfile.getFollowingCount() - 1);
                userProfileService.updateUserProfile(userProfile);
            }
            UserProfile targetProfile = userProfileService.selectUserProfileByUserId(followUserId);
            if (targetProfile != null && targetProfile.getFollowersCount() > 0)
            {
                targetProfile.setFollowersCount(targetProfile.getFollowersCount() - 1);
                userProfileService.updateUserProfile(targetProfile);
            }
            return AjaxResult.success("取消关注成功");
        }
        return AjaxResult.error("取消关注失败");
    }

    @GetMapping("/followers/{userId}")
    @ResponseBody
    public AjaxResult getFollowers(@PathVariable("userId") Long userId)
    {
        List<UserFollow> followers = userFollowService.selectFollowersByUserId(userId);
        return AjaxResult.success(followers);
    }

    @GetMapping("/following/{userId}")
    @ResponseBody
    public AjaxResult getFollowing(@PathVariable("userId") Long userId)
    {
        List<UserFollow> following = userFollowService.selectFollowingByUserId(userId);
        return AjaxResult.success(following);
    }

    // ==================== 浏览历史 ====================

    @GetMapping("/history/{userId}")
    @ResponseBody
    public TableDataInfo getBrowsingHistory(@PathVariable("userId") Long userId)
    {
        startPage();
        UserBrowsingHistory query = new UserBrowsingHistory();
        query.setUserId(userId);
        List<UserBrowsingHistory> list = browsingHistoryService.selectBrowsingHistoryList(query);
        return getDataTable(list);
    }

    @PostMapping("/history")
    @ResponseBody
    public AjaxResult addBrowsingHistory(@RequestBody Map<String, Object> params)
    {
        Long userId = Long.valueOf(params.get("userId").toString());
        Long articleId = Long.valueOf(params.get("articleId").toString());

        UserBrowsingHistory history = new UserBrowsingHistory();
        history.setUserId(userId);
        history.setArticleId(articleId);
        history.setBrowseTime(new Date());
        history.setCreateBy("api");

        int result = browsingHistoryService.insertBrowsingHistory(history);
        if (result > 0)
        {
            return AjaxResult.success("记录成功");
        }
        return AjaxResult.error("记录失败");
    }

    @DeleteMapping("/history/{userId}")
    @ResponseBody
    public AjaxResult clearBrowsingHistory(@PathVariable("userId") Long userId)
    {
        int result = browsingHistoryService.deleteBrowsingHistoryByUserId(userId);
        if (result > 0)
        {
            return AjaxResult.success("清空成功");
        }
        return AjaxResult.error("清空失败");
    }

    // ==================== 数据同步 ====================

    @PostMapping("/sync/{userId}")
    @ResponseBody
    public AjaxResult syncDataToCloud(@PathVariable("userId") Long userId, @RequestBody Map<String, Object> data)
    {
        UserProfile profile = userProfileService.selectUserProfileByUserId(userId);
        if (profile != null)
        {
            if (data.containsKey("nickname")) profile.setNickname(data.get("nickname").toString());
            if (data.containsKey("userBio")) profile.setBio(data.get("userBio").toString());
            if (data.containsKey("avatarPath")) profile.setAvatar(data.get("avatarPath").toString());
            if (data.containsKey("points")) profile.setPoints(Integer.valueOf(data.get("points").toString()));
            if (data.containsKey("cashBalance")) profile.setCashBalance(new java.math.BigDecimal(data.get("cashBalance").toString()));
            if (data.containsKey("lastCheckinDate")) {
                String dateStr = data.get("lastCheckinDate").toString();
                if (dateStr != null && !dateStr.isEmpty()) {
                    try {
                        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");
                        profile.setLastCheckin(sdf.parse(dateStr));
                    } catch (java.text.ParseException e) {
                        // 忽略日期解析错误
                    }
                }
            }
            profile.setUpdateBy("api");
            userProfileService.updateUserProfile(profile);
        }

        Map<String, Object> result = new HashMap<>();
        result.put("syncedAt", new Date());
        return AjaxResult.success(result);
    }

    @GetMapping("/sync/{userId}")
    @ResponseBody
    public AjaxResult syncDataFromCloud(@PathVariable("userId") Long userId)
    {
        UserProfile profile = userProfileService.selectUserProfileByUserId(userId);
        if (profile == null)
        {
            return AjaxResult.error("用户不存在");
        }

        Map<String, Object> data = new HashMap<>();
        data.put("nickname", profile.getNickname());
        data.put("userBio", profile.getBio());
        data.put("avatarPath", profile.getAvatar());
        data.put("points", profile.getPoints());
        data.put("cashBalance", profile.getCashBalance());
        data.put("lastCheckinDate", profile.getLastCheckin());
        data.put("followingCount", profile.getFollowingCount());
        data.put("followersCount", profile.getFollowersCount());
        data.put("level", profile.getLevel());

        UserInterest query = new UserInterest();
        query.setUserId(userId);
        List<UserInterest> interests = userInterestService.selectInterestList(query);
        data.put("interests", interests);

        return AjaxResult.success(data);
    }
}
