package com.project.scheduler.service.impl;

import com.project.scheduler.advice.TrackExecutionTime;
import com.project.scheduler.controllers.StudentController;
import com.project.scheduler.service.CacheCleanupService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class CacheCleanupServiceImpl implements CacheCleanupService {

    @Autowired
    CacheManager cacheManager;

    Logger logger = LoggerFactory.getLogger(CacheCleanupServiceImpl.class);

    @TrackExecutionTime
    @Scheduled(fixedRate = 600000) //Time is set for demonstrating purposes
    @Override
    public void clearTheWholeCache() {
        logger.warn("Clearing all the caches");
        cacheManager.getCacheNames().forEach(cacheName -> cacheManager.getCache(cacheName).clear());
    }

}
