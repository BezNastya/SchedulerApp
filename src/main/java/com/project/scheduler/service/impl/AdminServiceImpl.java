package com.project.scheduler.service.impl;

import com.project.scheduler.entity.Admin;
import com.project.scheduler.entity.ScheduleDate;
import com.project.scheduler.repository.AdminRepository;
import com.project.scheduler.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AdminServiceImpl implements AdminService {

//    private ScheduleService scheduleService;
    private final AdminRepository adminRepository;

    @Autowired
    public AdminServiceImpl(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }

/*
    @Override
    public void setNewLessonDate(ScheduleDate date) {

    }

    @Override
    public void deleteAdminById(Long adminId) {
        adminRepository.deleteById(adminId);
    }
*/

    @Override
    public Admin save(Admin admin) {
        return adminRepository.save(admin);
    }


    @Override
    public Optional<Admin> findByEmail(String email) {
        return adminRepository.findByEmail(email);
    }

    @Override
    public Optional<Admin> findById(Long id) {
        return adminRepository.findById(id);
    }

    @Override
    public List<Admin> findAll() {
        return adminRepository.findAll();
    }

    @Override
    public void delete(Admin admin) {
        adminRepository.delete(admin);
    }

    @Override
    public void update(Admin admin) {
        adminRepository.save(admin);
    }
}
