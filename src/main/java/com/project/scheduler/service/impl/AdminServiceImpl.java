package com.project.scheduler.service.impl;

import com.project.scheduler.entity.Admin;
import com.project.scheduler.entity.ScheduleDate;
import com.project.scheduler.repository.AdminRepository;
import com.project.scheduler.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminServiceImpl implements AdminService {

//    private ScheduleService scheduleService;
    private final AdminRepository adminRepository;

    @Autowired
    public AdminServiceImpl(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }


    @Override
    public void save() {

    }

    @Override
    public void setNewLessonDate(ScheduleDate date) {

    }




    @Override
    public Admin findByEmail(String email) {
        return adminRepository.findByEmail(email);
    }

    @Override
    public Admin findById(long id) {
        return adminRepository.findById(id);
    }


    //delete admin
    @Override
    public void deleteAdminById(long id) {
        adminRepository.deleteAdminById(id);
    }
}
