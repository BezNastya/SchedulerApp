package com.project.scheduler.service;

import com.project.scheduler.entity.Teacher;

public interface TeacherService {
    Teacher findById(long id);
    Teacher findByEmail(String email);
    Teacher findByLastName(String username);
}
