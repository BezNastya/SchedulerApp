package com.project.scheduler.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@Import(com.project.scheduler.config.CoreConfig.class)
public class AppConfig {

    @Value("dev")
    private String profile;

    private final Logger logger = LoggerFactory.getLogger(AppConfig.class);

    @Bean
    public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
        return args -> {

            logger.info("Beans created for SchedulerApplication:");

            String[] beans = ctx.getBeanDefinitionNames();
            for (String bean : beans) {
                logger.info("Bean " + bean + " was created");
            }

            logger.info("Profile " + profile);
        };
    }
}
