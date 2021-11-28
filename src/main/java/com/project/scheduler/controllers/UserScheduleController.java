package com.project.scheduler.controllers;

import com.project.scheduler.entity.Lesson;
import com.project.scheduler.entity.Student;
import com.project.scheduler.entity.Teacher;
import com.project.scheduler.entity.User;
import com.project.scheduler.service.StudentService;
import com.project.scheduler.service.TeacherService;
import com.project.scheduler.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;
import java.util.Objects;

@Controller
public class UserScheduleController {

    private final StudentService studentService;
    private final TeacherService teacherService;
    private final UserService userService;

    @Autowired
    public UserScheduleController(StudentService studentService,
                                  TeacherService teacherService,
                                  UserService userService) {
        this.studentService = studentService;
        this.teacherService = teacherService;
        this.userService = userService;
    }

    @Operation(summary = "Get user lessons")
    @GetMapping("/my-lessons")
    public String findLessonsByUser(Principal principal, Model model){
        User user = userService.findByEmail(principal.getName()).get();
        if(Objects.equals(user.getRole(), "STUDENT")){
//            return studentService.findLessonsByStudent((Student) user);
            model.addAttribute("lessonsByWeek", studentService.findLessonsByWeek(1,(Student) user));
            return "schedule";
        }
//        else if(Objects.equals(user.getRole(), "TEACHER")){
//            return teacherService.findLessonsByTeacher((Teacher) user);
//        }
        return null;
    }
}
