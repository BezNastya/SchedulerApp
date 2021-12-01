package com.project.scheduler.controllers;

import com.project.scheduler.entity.Teacher;
import com.project.scheduler.entity.User;
import com.project.scheduler.service.ExcelService;
import com.project.scheduler.service.StudentService;
import com.project.scheduler.service.TeacherService;
import com.project.scheduler.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.Optional;

@Controller
public class UserScheduleController {

    private final StudentService studentService;
    private final TeacherService teacherService;
    private final UserService userService;
    private final ExcelService excelService;

    @Autowired
    public UserScheduleController(StudentService studentService,
                                  TeacherService teacherService,
                                  UserService userService,
                                  ExcelService excelService) {
        this.studentService = studentService;
        this.teacherService = teacherService;
        this.userService = userService;
        this.excelService = excelService;
    }

    @Operation(summary = "Download Excel file")
    @GetMapping("/my-lessons/downloadExcel")
    public ResponseEntity<Resource> downloadExcel(Principal principal) {
        Optional<User> user =  userService.findByEmail(principal.getName());
       if (user.isPresent()) {
           long id = user.get().getUserId();
           InputStreamResource file = new InputStreamResource(excelService.getLessonsForStudent(id));
           return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + "schedule.xlsx")
                   .contentType(MediaType.parseMediaType("application/vnd.ms-excel"))
                   .body(file);
       }
       return ResponseEntity.notFound().build();
    }

    @Operation(summary = "Get user lessons")
    @GetMapping("/my-lessons")
    public String findLessonsByUser(Principal principal, Model model, @RequestParam(name="inputSelect",required = false, defaultValue = "1") Integer week){
        week = week < 1 ? 1: week;
        User user = userService.findByEmail(principal.getName()).get();
        if(user.getRole().equals("STUDENT")){
            model.addAttribute("lessonsByWeek",
                    studentService.findLessonsByWeekStudent(week, user.getUserId()));
            model.addAttribute("week", week);
        }
        else if(user.getRole().equals("TEACHER")){
            model.addAttribute("lessonsByWeek",
                    teacherService.findLessonsByWeekTeacher(week,(Teacher) user));
            model.addAttribute("week", week);
        }
        return "schedule";
    }
}
