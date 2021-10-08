package com.project.scheduler.service.impl;

import com.project.scheduler.entity.Admin;
import com.project.scheduler.entity.Course;
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
    public void setNewLessonDate(ScheduleDate date) {

    }

    @Override
    public void createCourse(Course course) {

    }

    @Override
    public void deleteCourse(long id) {

    }

    @Override
    public void addAdmin(Admin admin) {

    }


    @Override
    public Admin findByEmail(String email) {
        return new Admin();
    }

    @Override
    public Admin findById(Long id) {
        return adminRepository.findById(id).get();
    }


    //delete admin
    @Override
    public void deleteAdminById(Long id) {
        adminRepository.deleteById(id);
    }
}
