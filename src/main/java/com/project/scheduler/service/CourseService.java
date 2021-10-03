package com.project.scheduler.service;

import com.project.scheduler.entity.Course;
import com.project.scheduler.entity.Student;

import java.util.List;

public interface CourseService {
    List<Course> findAll();
    List<Course> findAllByStudent(Student student);
}
