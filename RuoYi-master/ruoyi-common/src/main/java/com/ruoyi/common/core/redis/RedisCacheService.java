package com.ruoyi.common.core.redis;

import java.util.Collection;
import java.util.concurrent.TimeUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

/**
 * Redis缓存工具类
 * 提供常用的缓存操作方法，统一使用news:前缀
 */
@Component
public class RedisCacheService {

    private static final Logger log = LoggerFactory.getLogger(RedisCacheService.class);

    /** Key前缀 */
    private static final String KEY_PREFIX = "news:";

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * 获取完整的Key（添加前缀）
     */
    private String getKey(String key) {
        return KEY_PREFIX + key;
    }

    /**
     * 设置缓存（永不过期）
     *
     * @param key   缓存键
     * @param value 缓存值
     */
    public void set(String key, Object value) {
        try {
            redisTemplate.opsForValue().set(getKey(key), value);
        } catch (Exception e) {
            log.error("Redis设置缓存失败, key={}", key, e);
        }
    }

    /**
     * 设置缓存带TTL
     *
     * @param key           缓存键
     * @param value         缓存值
     * @param timeoutSeconds 过期时间（秒）
     */
    public void set(String key, Object value, long timeoutSeconds) {
        try {
            redisTemplate.opsForValue().set(getKey(key), value, timeoutSeconds, TimeUnit.SECONDS);
        } catch (Exception e) {
            log.error("Redis设置缓存失败, key={}, timeout={}s", key, timeoutSeconds, e);
        }
    }

    /**
     * 获取缓存（反序列化为指定类型）
     *
     * @param key   缓存键
     * @param clazz 返回类型
     * @return 缓存值，不存在返回null
     */
    @SuppressWarnings("unchecked")
    public <T> T get(String key, Class<T> clazz) {
        try {
            Object value = redisTemplate.opsForValue().get(getKey(key));
            if (value != null && clazz.isInstance(value)) {
                return (T) value;
            }
            return null;
        } catch (Exception e) {
            log.error("Redis获取缓存失败, key={}", key, e);
            return null;
        }
    }

    /**
     * 删除缓存
     *
     * @param key 缓存键
     */
    public void delete(String key) {
        try {
            redisTemplate.delete(getKey(key));
        } catch (Exception e) {
            log.error("Redis删除缓存失败, key={}", key, e);
        }
    }

    /**
     * 判断key是否存在
     *
     * @param key 缓存键
     * @return true存在 false不存在
     */
    public boolean hasKey(String key) {
        try {
            return Boolean.TRUE.equals(redisTemplate.hasKey(getKey(key)));
        } catch (Exception e) {
            log.error("Redis判断key是否存在失败, key={}", key, e);
            return false;
        }
    }

    /**
     * 原子递增
     *
     * @param key   缓存键
     * @param delta 递增步长
     * @return 递增后的值
     */
    public Long increment(String key, long delta) {
        try {
            return redisTemplate.opsForValue().increment(getKey(key), delta);
        } catch (Exception e) {
            log.error("Redis递增失败, key={}, delta={}", key, delta, e);
            return null;
        }
    }

    /**
     * 原子递减
     *
     * @param key   缓存键
     * @param delta 递减步长
     * @return 递减后的值
     */
    public Long decrement(String key, long delta) {
        try {
            return redisTemplate.opsForValue().decrement(getKey(key), delta);
        } catch (Exception e) {
            log.error("Redis递减失败, key={}, delta={}", key, delta, e);
            return null;
        }
    }

    /**
     * 防击穿：获取并设置，如果不存在则用value填充
     *
     * @param key           缓存键
     * @param value         默认值（缓存未命中时使用）
     * @param timeoutSeconds 过期时间（秒）
     * @param clazz         返回类型
     * @return 缓存中的值或默认值
     */
    @SuppressWarnings("unchecked")
    public <T> T getAndSet(String key, Object value, long timeoutSeconds, Class<T> clazz) {
        try {
            String fullKey = getKey(key);
            Object cached = redisTemplate.opsForValue().get(fullKey);
            if (cached != null && clazz.isInstance(cached)) {
                return (T) cached;
            }
            // 缓存未命中，写入默认值
            redisTemplate.opsForValue().set(fullKey, value, timeoutSeconds, TimeUnit.SECONDS);
            if (clazz.isInstance(value)) {
                return (T) value;
            }
            return null;
        } catch (Exception e) {
            log.error("Redis getAndSet操作失败, key={}", key, e);
            if (clazz.isInstance(value)) {
                return (T) value;
            }
            return null;
        }
    }

    /**
     * 分布式锁：仅当key不存在时设置（SET NX EX）
     *
     * @param key           缓存键
     * @param value         锁值
     * @param timeoutSeconds 过期时间（秒）
     * @return true设置成功 false已存在
     */
    public Boolean setIfAbsent(String key, Object value, long timeoutSeconds) {
        try {
            return redisTemplate.opsForValue().setIfAbsent(getKey(key), value, timeoutSeconds, TimeUnit.SECONDS);
        } catch (Exception e) {
            log.error("Redis setIfAbsent操作失败, key={}, timeout={}s", key, timeoutSeconds, e);
            return false;
        }
    }

    /**
     * 批量删除匹配pattern的key
     *
     * @param pattern 匹配模式（如 article:detail:*）
     */
    public void deletePattern(String pattern) {
        try {
            String fullPattern = getKey(pattern);
            Collection<String> keys = redisTemplate.keys(fullPattern);
            if (keys != null && !keys.isEmpty()) {
                redisTemplate.delete(keys);
            }
        } catch (Exception e) {
            log.error("Redis批量删除失败, pattern={}", pattern, e);
        }
    }
}
