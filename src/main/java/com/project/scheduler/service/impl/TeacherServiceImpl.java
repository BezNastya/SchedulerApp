package com.project.scheduler.service.impl;

import com.project.scheduler.entity.Student;
import com.project.scheduler.entity.Teacher;
import com.project.scheduler.repository.TeacherRepository;
import com.project.scheduler.service.ScheduleService;
import com.project.scheduler.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TeacherServiceImpl implements TeacherService {

    private final TeacherRepository teacherRepository;

//    private ScheduleService scheduleService;

    @Autowired
    public TeacherServiceImpl(TeacherRepository teacherRepository) {
        this.teacherRepository = teacherRepository;
    }

//    @Autowired
//    public void setScheduleService(ScheduleService scheduleService) {
//        this.scheduleService = scheduleService;
//    }

    @Override
    public Teacher findById(long id) {
        return teacherRepository.findById(id);
    }

    @Override
    public Teacher findByEmail(String email) {
        return teacherRepository.findByEmail(email);
    }

    @Override
    public Teacher findByLastName(String lastName) {
        return teacherRepository.findByLastName(lastName);
    }
}
