package com.book.search.config;

import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.RemovalCause;
import com.github.benmanes.caffeine.cache.RemovalListener;
import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.springframework.cache.CacheManager;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PreDestroy;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

@Slf4j
@Configuration
public class CachedConfiguration {

    @Bean
    public CacheManager cacheManager(){
        SimpleCacheManager manager = new SimpleCacheManager();
        manager.setCaches(
            Arrays.asList(
                buildCache("keyword-top-ten", 5)
            ));
        return manager;
    }
    private CaffeineCache buildCache(String name, int secondsToExpire){
        return new CaffeineCache(name, Caffeine.newBuilder()
                .expireAfterWrite(secondsToExpire, TimeUnit.SECONDS)
                .removalListener(new CustomRemovalListener()).build());
    }

    @PreDestroy
    public void clearCache(){
        cacheManager().getCacheNames()
                .stream()
                .forEach(cache -> cacheManager().getCache(cache).clear());
    }

    class CustomRemovalListener implements RemovalListener<Object, Object> {
        @Override
        public void onRemoval(@Nullable Object key, @Nullable Object value, @NonNull RemovalCause cause) {
            log.debug("removal called with key {}, cause {}, evicted {} \n",
                    key, cause.toString(), cause.wasEvicted());
        }
    }
}
