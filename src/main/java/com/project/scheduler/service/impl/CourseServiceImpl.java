package com.project.scheduler.service.impl;

import com.project.scheduler.entity.Course;
import com.project.scheduler.entity.Student;
import com.project.scheduler.repository.CourseRepository;
import com.project.scheduler.service.CourseService;

import java.util.List;

public class CourseServiceImpl implements CourseService {
    CourseRepository courseRepository;
    @Override
    public List<Course> findAll() {
        return courseRepository.findAll();
    }

    @Override
    public List<Course> findAllByStudent(Student student) {
        return courseRepository.findAllByStudent(student);
    }
}
