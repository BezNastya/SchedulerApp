package com.project.scheduler.config;

import com.project.scheduler.cache.CustomCacheManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@EnableScheduling
@EnableCaching
@ComponentScan(basePackages = "com.project.scheduler")
@EnableJpaRepositories(basePackages = "com.project.scheduler.repository")
public class CoreConfig {

    private final Logger logger = LoggerFactory.getLogger(CoreConfig.class);

    @Bean
    public CacheManager cacheManager() {
        logger.info("Initializing the cache manager");
        return new CustomCacheManager();
    }
}
