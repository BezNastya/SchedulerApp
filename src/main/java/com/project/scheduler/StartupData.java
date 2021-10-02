package com.project.scheduler;

import com.project.scheduler.entity.Admin;
import com.project.scheduler.entity.Student;
import com.project.scheduler.entity.Teacher;
import com.project.scheduler.repository.MasterRepository;
import com.project.scheduler.repository.StudentRepository;
import com.project.scheduler.repository.TeacherRepository;
import com.project.scheduler.service.AdminService;
import com.project.scheduler.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class StartupData implements CommandLineRunner {

    private StudentRepository studentRepository;
    private TeacherRepository teacherRepository;
    private MasterRepository masterRepository;

    @Autowired
    public StartupData(StudentRepository studentRepository,
                       TeacherRepository teacherRepository,
                       MasterRepository masterRepository) {
    this.studentRepository = studentRepository;
    this.teacherRepository = teacherRepository;
    this.masterRepository = masterRepository;
    }

    @Override
    public void run(String... args) {
        adminAccount();
        studentAccount();
        teacherAccount();
    }

    private void studentAccount(){
        Student student = new Student();

        student.setEmail("student@ukma.edu.ua");
        student.setPassword("student");
        studentRepository.save(student);

    }
    private void teacherAccount(){
        Teacher teacher = new Teacher();

        teacher.setEmail("teacher@ukma.edu.ua");
        teacher.setPassword("teacher");
        teacherRepository.save(teacher);

    }
    private void adminAccount(){
        Admin admin = new Admin();

        admin.setEmail("admin@ukma.edu.ua");
        admin.setPassword("admin");
        masterRepository.save(admin);
    }
}