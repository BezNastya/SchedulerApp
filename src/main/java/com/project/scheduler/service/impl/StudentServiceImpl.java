package com.project.scheduler.service.impl;

import com.project.scheduler.service.ScheduleService;
import com.project.scheduler.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    private ScheduleService scheduleService;

}
