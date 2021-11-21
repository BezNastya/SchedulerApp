package com.project.scheduler.controllers;

import com.project.scheduler.entity.Student;
import com.project.scheduler.service.StudentService;
import io.swagger.v3.oas.annotations.Operation;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/student")
public class StudentController {

    Logger logger = LoggerFactory.getLogger(StudentController.class);
    Marker myMarker = MarkerFactory.getMarker("StudentClassMarker");

    private final StudentService studentService;
    @Autowired
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }


    @Operation(summary = "Get all the students")
    @GetMapping
    public String getAllStudents(Model model) {
        model.addAttribute("students",studentService.findAll());
        return "studentController";
    }


    @Operation(summary = "Get the student with the specified id")
    @GetMapping("/{id}")
    public String getStudentById(@PathVariable Long id, Model model){
        logger.info(myMarker, "Getting course with id {}", id);
        model.addAttribute("oneStudent",studentService.findById(id));
        return "studentController";

    }

    @Operation(summary = "Add a student with default parameters")
    @PostMapping
    public Student addStudent(@RequestBody Student student){
        logger.info(myMarker, "Adding student {}", student);
        return studentService.save(student);
    }

    @Operation(summary = "Change student`s faculty")
    @PutMapping("/{id}")
    public void updateStudentName(@PathVariable Long id, @RequestParam String newFaculty){
       Student student= studentService.findById(id);
        logger.info(myMarker, "Updating student faculty to {} for faculty with id {}", newFaculty, id);
        studentService.updateFaculty(student,newFaculty);
    }

    @Operation(summary = "Delete the student with the specified id")
    @DeleteMapping("/{id}")
    public String deleteStudent(@PathVariable long id){
        Student student =studentService.findById(id);

        studentService.delete(student);
        return" new student deleted";
    }
}