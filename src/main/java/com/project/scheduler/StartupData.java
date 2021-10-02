package com.project.scheduler;

import com.project.scheduler.entity.Admin;
import com.project.scheduler.entity.Student;
import com.project.scheduler.repository.AdminRepository;
import com.project.scheduler.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class StartupData implements CommandLineRunner {

    private StudentRepository studentRepository;
    private AdminRepository adminRepository;

    @Autowired
    public StartupData(StudentRepository studentRepository, AdminRepository adminRepository) {
    this.studentRepository = studentRepository;
    this.adminRepository = adminRepository;
    }

    @Override
    public void run(String... args) {
        adminAccount();
        studentAccount();
    }

    private void studentAccount(){
        Student student = new Student();

        student.setEmail("student@ukma.edu.ua");
        student.setPassword("student");
        studentRepository.save(student);

    }
    private void adminAccount(){
        Admin admin = new Admin();

        admin.setEmail("admin@ukma.edu.ua");
        admin.setPassword("admin");

        //TODO  masterRepository and uncomment function below
         adminRepository.save(admin);
    }
}