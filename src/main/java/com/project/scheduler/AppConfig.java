package com.project.scheduler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@EnableScheduling
@EnableCaching
@ComponentScan
@EnableJpaRepositories
public class AppConfig {

    @Value("${spring.profiles.active}")
    private String profile;

    private final Logger logger = LoggerFactory.getLogger(AppConfig.class);

    @Bean
    public CacheManager cacheManager() {
        logger.info("Initializing the cache manager");
        return new CustomCacheManager();
    }

    @Bean
    public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
        return args -> {

            System.out.println("Beans created for SchedulerApplication:");

            String[] beans = ctx.getBeanDefinitionNames();
            for (String bean : beans) {
                System.out.println("Bean " + bean + " was created");
            }

            System.err.println("Profile " + profile);
        };
    }
}
