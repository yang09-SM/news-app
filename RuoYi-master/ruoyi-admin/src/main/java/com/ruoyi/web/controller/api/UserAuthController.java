package com.ruoyi.web.controller.api;

import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.ruoyi.common.annotation.Anonymous;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.utils.BcryptPasswordService;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.framework.shiro.jwt.JwtUtil;
import com.ruoyi.system.domain.UserProfile;
import com.ruoyi.system.service.ISysUserService;
import com.ruoyi.system.service.IUserProfileService;

@Controller
@RequestMapping("/api/auth")
@Anonymous
public class UserAuthController extends BaseController
{
    @Autowired
    private ISysUserService userService;

    @Autowired
    private IUserProfileService userProfileService;

    @PostMapping("/register")
    @ResponseBody
    public Map<String, Object> register(@RequestBody Map<String, String> request)
    {
        Map<String, Object> response = new HashMap<>();
        String username = request.get("username");
        String password = request.get("password");

        if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password))
        {
            response.put("success", false);
            response.put("message", "用户名和密码不能为空");
            return response;
        }

        SysUser checkUser = userService.selectUserByLoginName(username);
        if (checkUser != null)
        {
            response.put("success", false);
            response.put("message", "用户名已存在");
            return response;
        }

        SysUser user = new SysUser();
        user.setLoginName(username);
        user.setUserName(username);
        user.setPassword(BcryptPasswordService.encryptPassword(password));
        user.setSalt("bcrypt");
        user.setStatus("0");
        user.setUserType("00");
        user.setSex("2");
        user.setCreateBy("api");

        try
        {
            int result = userService.insertUser(user);
            if (result > 0)
            {
                UserProfile userProfile = new UserProfile();
                userProfile.setUserId(user.getUserId());
                userProfile.setNickname(username);
                userProfile.setPoints(0);
                userProfile.setCashBalance(java.math.BigDecimal.ZERO);
                userProfile.setCheckinDays(0);
                userProfile.setFollowingCount(0);
                userProfile.setFollowersCount(0);
                userProfile.setFriendsCount(0);
                userProfile.setLikesCount(0);
                userProfile.setLevel(1);
                userProfile.setVipLevel(0);
                userProfile.setCreateBy("api");
                userProfileService.insertUserProfile(userProfile);

                // 注册成功后自动登录，返回Token
                String token = JwtUtil.generateToken(user.getUserId(), username);
                String refreshToken = JwtUtil.generateRefreshToken(user.getUserId(), username);

                Map<String, Object> userData = new HashMap<>();
                userData.put("id", user.getUserId().toString());
                userData.put("username", username);

                response.put("success", true);
                response.put("message", "注册成功");
                response.put("user", userData);
                response.put("token", token);
                response.put("refreshToken", refreshToken);
            }
            else
            {
                response.put("success", false);
                response.put("message", "注册失败");
            }
        }
        catch (Exception e)
        {
            response.put("success", false);
            response.put("message", "注册失败：" + e.getMessage());
        }

        return response;
    }

    @PostMapping("/login")
    @ResponseBody
    public Map<String, Object> login(@RequestBody Map<String, String> request)
    {
        Map<String, Object> response = new HashMap<>();
        String username = request.get("username");
        String password = request.get("password");

        if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password))
        {
            response.put("success", false);
            response.put("message", "用户名和密码不能为空");
            return response;
        }

        SysUser user = userService.selectUserByLoginName(username);
        if (user == null)
        {
            response.put("success", false);
            response.put("message", "用户名或密码错误");
            return response;
        }

        boolean isValid = false;
        if ("bcrypt".equals(user.getSalt()))
        {
            isValid = BcryptPasswordService.checkPassword(password, user.getPassword());
        }

        if (!isValid)
        {
            response.put("success", false);
            response.put("message", "用户名或密码错误");
            return response;
        }

        // 生成JWT Token
        String token = JwtUtil.generateToken(user.getUserId(), username);
        String refreshToken = JwtUtil.generateRefreshToken(user.getUserId(), username);

        Map<String, Object> userData = new HashMap<>();
        userData.put("id", user.getUserId().toString());
        userData.put("username", username);

        response.put("success", true);
        response.put("message", "登录成功");
        response.put("user", userData);
        response.put("token", token);
        response.put("refreshToken", refreshToken);

        return response;
    }

    @PostMapping("/change-password")
    @ResponseBody
    public Map<String, Object> changePassword(@RequestBody Map<String, String> request)
    {
        Map<String, Object> response = new HashMap<>();
        String username = request.get("username");
        String oldPassword = request.get("oldPassword");
        String newPassword = request.get("newPassword");

        if (StringUtils.isEmpty(username) || StringUtils.isEmpty(oldPassword) || StringUtils.isEmpty(newPassword))
        {
            response.put("success", false);
            response.put("message", "缺少必要参数");
            return response;
        }

        SysUser user = userService.selectUserByLoginName(username);
        if (user == null)
        {
            response.put("success", false);
            response.put("message", "用户不存在");
            return response;
        }

        boolean isValid = false;
        if ("bcrypt".equals(user.getSalt()))
        {
            isValid = BcryptPasswordService.checkPassword(oldPassword, user.getPassword());
        }

        if (!isValid)
        {
            response.put("success", false);
            response.put("message", "旧密码错误");
            return response;
        }

        user.setPassword(BcryptPasswordService.encryptPassword(newPassword));
        user.setUpdateBy("api");
        int result = userService.updateUser(user);

        if (result > 0)
        {
            // 密码修改成功后生成新Token
            String newToken = JwtUtil.generateToken(user.getUserId(), username);
            String newRefreshToken = JwtUtil.generateRefreshToken(user.getUserId(), username);

            response.put("success", true);
            response.put("message", "密码修改成功");
            response.put("token", newToken);
            response.put("refreshToken", newRefreshToken);
        }
        else
        {
            response.put("success", false);
            response.put("message", "密码修改失败");
        }

        return response;
    }

    /**
     * 刷新Token
     */
    @PostMapping("/refresh-token")
    @ResponseBody
    public Map<String, Object> refreshToken(@RequestBody Map<String, String> request)
    {
        Map<String, Object> response = new HashMap<>();
        String refreshToken = request.get("refreshToken");

        if (StringUtils.isEmpty(refreshToken))
        {
            response.put("success", false);
            response.put("message", "refreshToken不能为空");
            return response;
        }

        if (!JwtUtil.validateToken(refreshToken) || !JwtUtil.isRefreshToken(refreshToken))
        {
            response.put("success", false);
            response.put("message", "refreshToken无效或已过期");
            return response;
        }

        Long userId = JwtUtil.getUserId(refreshToken);
        String username = JwtUtil.getUsername(refreshToken);

        SysUser user = userService.selectUserById(userId);
        if (user == null)
        {
            response.put("success", false);
            response.put("message", "用户不存在");
            return response;
        }

        // 生成新的Token对
        String newToken = JwtUtil.generateToken(userId, username);
        String newRefreshToken = JwtUtil.generateRefreshToken(userId, username);

        response.put("success", true);
        response.put("message", "刷新成功");
        response.put("token", newToken);
        response.put("refreshToken", newRefreshToken);

        return response;
    }
}
