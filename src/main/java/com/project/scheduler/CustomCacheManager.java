package com.project.scheduler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.concurrent.ConcurrentMapCache;

import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.CountDownLatch;


public class CustomCacheManager implements CacheManager {

    private static final int INITIAL_CAPACITY = 16;

    private final Logger logger = LoggerFactory.getLogger(CacheManager.class);

    private final ConcurrentMap<String, Cache> cache = new ConcurrentHashMap<>(INITIAL_CAPACITY);

    //private final Set<String> cacheNames = new HashSet<>(Arrays.asList("users", "groups"));

    @Override
    public Cache getCache(String s) {
        logger.info("Getting cache for " + s);

        Cache value = cache.get(s);
        if (value != null) {
            return value;
        } else {
            logger.warn("There is no cache associated with " + s + ". Creating the corresponding one.");
            value = new ConcurrentMapCache(s);
            cache.put(s, value);
        }
        return value;

    }

    @Override
    public Collection<String> getCacheNames() {
        return cache.keySet();
    }


}
