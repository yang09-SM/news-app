package com.ruoyi.common.config;

import org.springframework.cache.CacheManager;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

/**
 * 缓存配置类
 * 提供基础CacheManager（基于ConcurrentMap的内存缓存）
 * 解决Spring Boot启动时CacheManager缺失问题
 */
@Configuration
public class RedisConfig {

    @Bean
    @Primary
    public CacheManager cacheManager() {
        return new ConcurrentMapCacheManager();
    }
}
