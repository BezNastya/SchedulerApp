package com.project.scheduler.controllers;

import com.project.scheduler.entity.EducationUser;
import com.project.scheduler.entity.GroupCourse;
import com.project.scheduler.entity.Student;
import com.project.scheduler.service.CourseService;
import com.project.scheduler.service.StudentService;
import com.project.scheduler.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
public class GroupController {
    private final StudentService studentService;
    private final CourseService courseService;

    public GroupController(StudentService studentService, CourseService courseService) {
        this.studentService = studentService;
        this.courseService = courseService;
    }

    @GetMapping("/govno")
    public Set<GroupCourse> getAdminById() {
        return studentService.addGroupForUser(2L, courseService.findCourseByName("Test1").get(), (byte)5).getGroupCourse();
    }

    @GetMapping("/govno1")
    public Set<GroupCourse> geAdminById() {
        return studentService.deleteGroupForUser(2L, courseService.findCourseByName("Test1").get(), (byte)5).getGroupCourse();
    }
}
