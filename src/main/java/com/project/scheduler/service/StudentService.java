package com.project.scheduler.service;

import com.project.scheduler.entity.Course;
import com.project.scheduler.entity.Student;

import java.util.List;


public interface StudentService {
    Student findByLastName(String username);
    Student findByEmail(String email);
    Student findById(long id);
    List<Student>findAll();
    void joinCourse(String course,Student student);
}
