package com.ruoyi.framework.shiro.jwt;

import java.util.Date;
import javax.crypto.SecretKey;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

/**
 * JWT工具类
 */
public class JwtUtil
{
    private static final SecretKey SECRET_KEY = Keys.hmacShaKeyFor(
        "RuoYiNewsAppJwtSecretKey2026MustBe256BitsLong!!".getBytes()
    );

    /** Token有效期：7天 */
    private static final long EXPIRE_TIME = 7 * 24 * 60 * 60 * 1000L;

    /** 刷新Token有效期：30天 */
    private static final long REFRESH_EXPIRE_TIME = 30 * 24 * 60 * 60 * 1000L;

    /**
     * 生成JWT Token
     */
    public static String generateToken(Long userId, String username)
    {
        return Jwts.builder()
                .subject(userId.toString())
                .claim("username", username)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + EXPIRE_TIME))
                .signWith(SECRET_KEY)
                .compact();
    }

    /**
     * 生成刷新Token
     */
    public static String generateRefreshToken(Long userId, String username)
    {
        return Jwts.builder()
                .subject(userId.toString())
                .claim("username", username)
                .claim("type", "refresh")
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + REFRESH_EXPIRE_TIME))
                .signWith(SECRET_KEY)
                .compact();
    }

    /**
     * 从Token中获取用户ID
     */
    public static Long getUserId(String token)
    {
        Claims claims = parseToken(token);
        if (claims != null)
        {
            return Long.parseLong(claims.getSubject());
        }
        return null;
    }

    /**
     * 从Token中获取用户名
     */
    public static String getUsername(String token)
    {
        Claims claims = parseToken(token);
        if (claims != null)
        {
            return claims.get("username", String.class);
        }
        return null;
    }

    /**
     * 验证Token是否有效
     */
    public static boolean validateToken(String token)
    {
        try
        {
            parseToken(token);
            return true;
        }
        catch (Exception e)
        {
            return false;
        }
    }

    /**
     * 判断Token是否即将过期（小于1天）
     */
    public static boolean isTokenExpiringSoon(String token)
    {
        Claims claims = parseToken(token);
        if (claims != null)
        {
            long expireTime = claims.getExpiration().getTime();
            long remaining = expireTime - System.currentTimeMillis();
            return remaining < 24 * 60 * 60 * 1000L;
        }
        return true;
    }

    /**
     * 判断是否为刷新Token
     */
    public static boolean isRefreshToken(String token)
    {
        Claims claims = parseToken(token);
        if (claims != null)
        {
            return "refresh".equals(claims.get("type", String.class));
        }
        return false;
    }

    /**
     * 解析Token
     */
    private static Claims parseToken(String token)
    {
        try
        {
            return Jwts.parser()
                    .verifyWith(SECRET_KEY)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
        }
        catch (Exception e)
        {
            return null;
        }
    }
}
