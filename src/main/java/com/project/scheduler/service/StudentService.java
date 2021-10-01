package com.project.scheduler.service;

import com.project.scheduler.entity.Student;


public interface StudentService {
    Student findByLastName(String username);
    Student findByEmail(String email);
    Student findById(long id);
}
