package com.project.scheduler.service.impl;

import com.project.scheduler.entity.Student;
import com.project.scheduler.repository.StudentRepository;
import com.project.scheduler.service.ScheduleService;
import com.project.scheduler.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudentServiceImpl implements StudentService {
   private final StudentRepository studentRepository;


//    @Autowired
//    private ScheduleService scheduleService;

    @Autowired
    public StudentServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public Student findByLastName(String username) {
        return studentRepository.findByLastName(username);
    }

    @Override
    public Student findByEmail(String email) {
        return studentRepository.findByEmail(email);
    }

    @Override
    public Student findById(long id) {
        return studentRepository.findById(id);
    }
}
