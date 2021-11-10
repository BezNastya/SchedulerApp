package com.project.scheduler.controllers;

import com.project.scheduler.StartupData;
import com.project.scheduler.entity.Teacher;
import com.project.scheduler.exceptions.UserNotFoundException;
import com.project.scheduler.service.TeacherService;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

@RestController
public class TeacherController {

    private final Logger logger = LoggerFactory.getLogger(StartupData.class);
    private final static Marker myMarker = MarkerFactory.getMarker("TeacherControllerMarker");

    @Autowired
    TeacherService teacherService;

    @GetMapping("/teacher")
    public List<Teacher> getTeachers() {
        return teacherService.findAll();
    }

    @GetMapping("/teacher/{id}")
    public Optional<Teacher> getTeacher(@PathVariable final long id) {
        return teacherService.findById(id);
    }

    @PostMapping("/teacher/add")
    public Teacher addTeacher(@RequestBody @Valid Teacher teacher) {
        logger.warn(myMarker, "Adding new teacher");
        return teacherService.save(teacher);
    }

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

    @PutMapping("/teacher/editAcademicDegree//{id}")
    public String editTeacher(@RequestBody @NotNull @NotBlank(message = "Academic degree must not be blank")
                                           String degree, @PathVariable final long id) {
        teacherService.updateAcademicDegree(id, degree);
        return "Teacher`s degree has been changed to " + degree;
    }


    @DeleteMapping("teacher/remove/{id}")
    public String removeTeacher(@PathVariable final long id) {
        teacherService.delete(id);
        return ("Teacher with id " + id + " has been removed");
    }
}

