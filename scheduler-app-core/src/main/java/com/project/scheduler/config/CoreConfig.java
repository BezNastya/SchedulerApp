package com.project.scheduler.config;

import org.springframework.cache.annotation.EnableCaching;
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
}
