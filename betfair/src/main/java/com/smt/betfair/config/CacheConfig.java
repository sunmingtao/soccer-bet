package com.smt.betfair.config;

import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

@Configuration
@EnableCaching
public class CacheConfig {

    private final long sessionTokenExireInHours;

    public CacheConfig(
            @Value("${token.expire.in.hours}") final long sessionTokenExireInHours) {
        this.sessionTokenExireInHours = sessionTokenExireInHours;
    }

    @Bean
    public CacheManager cacheManager() {
        final CaffeineCache loginCache =
                buildCache("loginCache", sessionTokenExireInHours, TimeUnit.HOURS);
        final SimpleCacheManager cacheManager = new SimpleCacheManager();
        cacheManager.setCaches(Arrays.asList(loginCache));
        return cacheManager;
    }

    private CaffeineCache buildCache(final String name, final long expire, final TimeUnit timeUnit) {
        return new CaffeineCache(
                name, Caffeine.newBuilder().expireAfterWrite(expire, timeUnit).build());
    }
}