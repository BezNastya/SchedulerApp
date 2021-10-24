package com.project.scheduler.service;

import com.project.scheduler.entity.Admin;
import com.project.scheduler.entity.ScheduleDate;

import java.util.List;

public interface AdminService {

    void setNewLessonDate(ScheduleDate date);

    void deleteAdminById(Long adminId);

    Admin findByEmail(String email);
    Admin findById(Long id);
    List<Admin> findAll();
    void delete(Admin admin);
    void update(Admin admin);
    Admin save(Admin admin);

}
