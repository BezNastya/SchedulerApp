package com.project.scheduler.service;

import com.project.scheduler.entity.Admin;
import com.project.scheduler.entity.Course;
import com.project.scheduler.entity.ScheduleDate;

import java.util.List;

public interface AdminService {

    void setNewLessonDate(ScheduleDate date);
    void createCourse(Course course);
    void deleteCourse(long id);


    Admin findByEmail(String email);
    Admin findById(Long id);
    List<Admin> findAll();
    void deleteAdmin(Admin admin);
    void updateAdmin(Admin admin);
    void saveAdmin(Admin admin);

}
