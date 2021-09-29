package com.project.scheduler.service.impl;

import com.project.scheduler.service.AdminService;
import com.project.scheduler.service.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminServiceImpl implements AdminService {

    private ScheduleService scheduleService;

    @Autowired
    public AdminServiceImpl(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

}
