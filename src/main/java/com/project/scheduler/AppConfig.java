package com.project.scheduler;

import com.project.scheduler.entity.Course;
import com.project.scheduler.entity.GroupCourse;
import com.project.scheduler.repository.CourseRepository;
import com.project.scheduler.repository.GroupCourseRepository;
import com.project.scheduler.service.CourseService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@ComponentScan
@EnableJpaRepositories
public class AppConfig {
    @Value("${spring.profiles.active}")
    private String profile;

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
