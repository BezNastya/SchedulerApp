package com.project.scheduler.service;

import com.project.scheduler.entity.Admin;
import com.project.scheduler.entity.Course;
import com.project.scheduler.entity.ScheduleDate;

public interface AdminService {

    void setNewLessonDate(ScheduleDate date);
    void createCourse(Course course);
    void deleteCourse(long id);
    void addAdmin(Admin admin);

    Admin findByEmail(String email);
    Admin findById(Long id);
    void deleteAdminById(Long id);

}
