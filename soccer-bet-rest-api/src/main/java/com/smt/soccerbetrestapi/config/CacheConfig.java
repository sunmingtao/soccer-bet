package com.smt.soccerbetrestapi.config;

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

    private final long matchExireInHours;

    public CacheConfig(
            @Value("${match.expire.in.hours}") final long matchExireInHours) {
        this.matchExireInHours = matchExireInHours;
    }

    @Bean
    public CacheManager cacheManager() {
        final CaffeineCache allocatedToListCache =
                buildCache("matchesCache", matchExireInHours, TimeUnit.HOURS);
        final SimpleCacheManager cacheManager = new SimpleCacheManager();
        cacheManager.setCaches(Arrays.asList(allocatedToListCache));
        return cacheManager;
    }

    private CaffeineCache buildCache(final String name, final long expire, final TimeUnit timeUnit) {
        return new CaffeineCache(
                name, Caffeine.newBuilder().expireAfterWrite(expire, timeUnit).build());
    }
}
