package com.ruoyi.framework.shiro.jwt;

import java.io.IOException;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.system.service.ISysUserService;

/**
 * JWT认证过滤器
 * 从请求头中提取JWT Token，验证并设置用户信息到请求属性
 */
public class JwtAuthenticationFilter implements Filter
{
    private ISysUserService userService;

    public void setUserService(ISysUserService userService)
    {
        this.userService = userService;
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException
    {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException
    {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        String token = getTokenFromRequest(httpRequest);

        if (StringUtils.isNotEmpty(token) && JwtUtil.validateToken(token))
        {
            Long userId = JwtUtil.getUserId(token);
            if (userId != null && userService != null)
            {
                SysUser user = userService.selectUserById(userId);
                if (user != null)
                {
                    // 将用户信息设置到请求属性中，供Controller使用
                    httpRequest.setAttribute("currentUser", user);
                    httpRequest.setAttribute("currentUserId", userId);
                }
            }
        }

        chain.doFilter(request, response);
    }

    @Override
    public void destroy()
    {
    }

    /**
     * 从请求头中提取Token
     */
    private String getTokenFromRequest(HttpServletRequest request)
    {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.isNotEmpty(bearerToken) && bearerToken.startsWith("Bearer "))
        {
            return bearerToken.substring(7);
        }
        return null;
    }
}
