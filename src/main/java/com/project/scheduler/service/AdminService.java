package com.project.scheduler.service;

import com.project.scheduler.entity.Admin;
import com.project.scheduler.entity.ScheduleDate;

import java.util.List;
import java.util.Optional;

public interface AdminService {

    //void setNewLessonDate(ScheduleDate date);

    //void deleteAdminById(Long adminId);

    Optional<Admin> findByEmail(String email);
    Optional<Admin> findById(Long id);
    List<Admin> findAll();
    void delete(Admin admin);
    void update(Admin admin);
    Admin save(Admin admin);

}
