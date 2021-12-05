package com.project.scheduler.service.impl;

import com.project.scheduler.service.HomeService;
import com.project.scheduler.service.StudentService;
import com.project.scheduler.service.TeacherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class HomeServiceImpl implements HomeService {
    private final Logger logger = LoggerFactory.getLogger(HomeServiceImpl.class);
    private final StudentService studentService;
    private final TeacherService teacherService;

    public HomeServiceImpl(StudentService studentService, TeacherService teacherService) {
        this.studentService = studentService;
        this.teacherService = teacherService;
    }


    //cron for NY
    @Scheduled( cron = "0 0 0 01 01 ?", zone="Europe/Kiev")
    public void happyNewYear() {

        logger.warn("happy New Year! we are going to the next year with "+studentService.findAll().size()
                +"students and  "+teacherService.findAll().size()+" teachers" );
    }


    //cron for every month
    @Scheduled( cron = "0 0 1 * * ?", zone="Europe/Kiev")
    public void monthStat() {

        logger.warn("happy New Year! we are going to the next year with "+studentService.findAll().size()
                +"students and  "+teacherService.findAll().size()+" teachers" );
    }

    @Scheduled( cron = "0 0 13 * * *", zone="Europe/Kiev")
    public void goodDay() {
        System.out.println("New month! we have  "+studentService.findAll().size()
                +"students and  "+teacherService.findAll().size()+" teachers" );
    }
    @Scheduled( cron = " 0 0 * * 6-7 ?", zone="Europe/Kiev")
    public void happyWeekend() {
        System.out.println("New month! we have  "+studentService.findAll().size()
                +"students and  "+teacherService.findAll().size()+" teachers" );
    }

    //cron for every 2 minutes -   0 0/2 * * * ?

}
