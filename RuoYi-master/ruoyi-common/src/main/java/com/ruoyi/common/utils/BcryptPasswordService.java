package com.ruoyi.common.utils;

import org.mindrot.jbcrypt.BCrypt;

/**
 * BCrypt密码加密工具类
 * 用于与Node.js的bcryptjs兼容，使用相同的salt rounds（10）
 */
public class BcryptPasswordService
{
    private static final int SALT_ROUNDS = 10;

    /**
     * 加密密码
     * 
     * @param plainPassword 明文密码
     * @return 加密后的密码
     */
    public static String encryptPassword(String plainPassword)
    {
        return BCrypt.hashpw(plainPassword, BCrypt.gensalt(SALT_ROUNDS));
    }

    /**
     * 验证密码
     * 
     * @param plainPassword 明文密码
     * @param hashedPassword 加密后的密码
     * @return 是否匹配
     */
    public static boolean checkPassword(String plainPassword, String hashedPassword)
    {
        return BCrypt.checkpw(plainPassword, hashedPassword);
    }
}
