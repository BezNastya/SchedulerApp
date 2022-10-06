package com.project.scheduler.controllers.rest;

import com.project.scheduler.entity.Admin;
import com.project.scheduler.entity.Teacher;
import com.project.scheduler.exceptions.UserNotFoundException;
import com.project.scheduler.service.AdminService;
import com.project.scheduler.service.TeacherService;
import io.swagger.v3.oas.annotations.Operation;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

@RestController
public class TeacherController {

    private final Logger logger = LoggerFactory.getLogger(TeacherController.class);
    private final static Marker myMarker = MarkerFactory.getMarker("TeacherControllerMarker");

    private final TeacherService teacherService;

    @Autowired
    public TeacherController(TeacherService teacherService) {
        this.teacherService = teacherService;
    }

    @Operation(summary = "Get all the teachers")
    @GetMapping("/teacher")
    public List<Teacher> getTeachers() {
        return teacherService.findAll();
    }

    @Operation(summary = "Get the teacher with the specified id")
    @GetMapping("/teacher/{id}")
    public Optional<Teacher> getTeacher(@PathVariable final long id) {
        return teacherService.findById(id);
    }

    @Operation(summary = "Add a teacher with default parameters")
    @PostMapping("/teacher/add")
    public Teacher addTeacher(@RequestBody @Valid Teacher teacher) {
        logger.warn(myMarker, "Adding new teacher");
        return teacherService.save(teacher);
    }

    @Operation(summary = "Change the teacher")
    @PutMapping("/teacher/edit")
    public Teacher editTeacher(@RequestBody @Valid Teacher updatedTeacher) {
        Teacher teacher = teacherService.findById(updatedTeacher.getUserId()).orElseThrow(
                () -> new UserNotFoundException(updatedTeacher.getUserId()));
        teacher.setDepartment(updatedTeacher.getDepartment());
        teacher.setAcademicDegree(updatedTeacher.getAcademicDegree());
        MDC.put("Editing teacher with id: ", String.valueOf(updatedTeacher.getUserId()));
        teacherService.save(teacher);
        MDC.clear();
        return teacher;
    }

    @Operation(summary = "Edit the academic degree of the specified teacher")
    @PutMapping("/teacher/editAcademicDegree//{id}")
    public String editTeacher(@RequestBody @NotNull @NotBlank(message = "Academic degree must not be blank")
                                           String degree, @PathVariable final long id) {
        teacherService.updateAcademicDegree(id, degree);
        return "Teacher`s degree has been changed to " + degree;
    }


    @Operation(summary = "Remove the teacher with the specified id")
    @DeleteMapping("teacher/remove/{id}")
    public String removeTeacher(@PathVariable final long id) {
        teacherService.delete(id);
        return ("Teacher with id " + id + " has been removed");
    }
}

