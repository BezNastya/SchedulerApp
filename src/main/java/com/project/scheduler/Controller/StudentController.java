package com.project.scheduler.controller;

import com.project.scheduler.entity.Student;
import com.project.scheduler.entity.User;
import com.project.scheduler.service.StudentService;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedList;
import java.util.List;

@RestController
public class StudentController {

    Logger logger = LoggerFactory.getLogger(StudentController.class);
    Marker myMarker = MarkerFactory.getMarker("StudentClassMarker");
    @Autowired
    StudentService studentService;


    @GetMapping("/student/{id}")
    public String clientMCDRequest(@PathVariable String id) throws InterruptedException {
        MDC.put("studentId", id);

        logger.info(myMarker, "students {} has made a request", id);
        logger.info(myMarker, "Starting request");
        Thread.sleep(5000);
        logger.info(myMarker, "Finished request");
        MDC.clear();
        return "finished";
    }

    @GetMapping("/student")
    public List<Student> getStudents() {
        List<Student> listOfStudents = studentService.findAll();
        return listOfStudents;
    }

    @PostMapping("/student/add")
    public String addStudent(){
        User student= new Student();
        student.setFirstName("Maks");
        student.setEmail("Maks@mail.com");
        studentService.save((Student) student);
        return" new student added";
    }


    @PutMapping("/student/edit/{id}")
    public String editStudent(@PathVariable long id){
        Student student =studentService.findById(id);
        student.setFirstName("Maks");

        studentService.save(student);
        return"  student updated";
    }

    @DeleteMapping("/student/delete/{id}")
    public String deleteStudent(@PathVariable long id){
        Student student =studentService.findById(id);

        studentService.delete(student);
        return" new student deleted";
    }
}