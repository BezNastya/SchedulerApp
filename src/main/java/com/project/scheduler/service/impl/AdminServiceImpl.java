package com.project.scheduler.service.impl;

import com.project.scheduler.entity.Admin;
import com.project.scheduler.repository.MasterRepository;
import com.project.scheduler.service.AdminService;
import com.project.scheduler.service.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminServiceImpl implements AdminService {

//    private ScheduleService scheduleService;
    private final MasterRepository masterRepository;

    @Autowired
    public AdminServiceImpl(MasterRepository masterRepository) {
        this.masterRepository = masterRepository;
    }


    @Override
    public void save() {

    }

    @Override
    public Admin findByEmail(String email) {
        return masterRepository.findByEmail(email);
    }

    @Override
    public Admin findById(long id) {
        return masterRepository.findById(id);
    }
}
