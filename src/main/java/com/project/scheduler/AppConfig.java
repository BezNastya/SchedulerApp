package com.project.scheduler;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

@Configuration
@ComponentScan
public class AppConfig {

    @Bean
    public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
        return args -> {

            System.out.println("Beans created for SchedulerApplication:");

            String[] beans = ctx.getBeanDefinitionNames();
            for (String bean : beans) {
                System.out.println("Bean " + bean + " was created");
            }
        };
    }
}
