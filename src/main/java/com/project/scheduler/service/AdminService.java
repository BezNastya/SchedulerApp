package com.project.scheduler.service;

import com.project.scheduler.entity.Admin;
import com.project.scheduler.entity.ScheduleDate;

public interface AdminService {
    void save();
    void setNewLessonDate(ScheduleDate date);

    Admin findByEmail(String email);
    Admin findById(long id);
    void deleteAdminById(long id);

}
