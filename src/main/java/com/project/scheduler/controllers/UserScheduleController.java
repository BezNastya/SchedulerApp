package com.project.scheduler.controllers;

import com.project.scheduler.entity.Role;
import com.project.scheduler.entity.User;
import com.project.scheduler.exceptions.UserNotFoundException;
import com.project.scheduler.service.*;
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

    private final CourseService courseService;
    private final UserService userService;
    private final ExcelService excelService;

    @Autowired
    public UserScheduleController(CourseService courseService,
                                  UserService userService,
                                  ExcelService excelService) {
        this.courseService = courseService;
        this.userService = userService;
        this.excelService = excelService;
    }

    @Operation(summary = "Download the schedule in an Excel file")
    @GetMapping("/my-lessons/downloadExcel")
    public ResponseEntity<Resource> downloadExcel(Principal principal, @RequestParam("week") Integer week) {
        if (week <= 0 || week > 15) {
            return ResponseEntity.notFound().build();
        }
        User user = userService.findByEmail(principal.getName()).orElseThrow(() -> new UserNotFoundException(principal.getName()));
        long id = user.getUserId();
        InputStreamResource file = new InputStreamResource(excelService.getScheduleForStudentForSpecifiedWeek(id, week));
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=" + user.getFirstName() + '_' + user.getLastName() + ".xlsx")
                .contentType(MediaType.parseMediaType("application/vnd.ms-excel"))
                .body(file);
    }

    @Operation(summary = "Get the user`s lessons")
    @GetMapping("/my-lessons")
    public String findLessonsByUser(Principal principal, Model model, @RequestParam(name="inputSelect",required = false, defaultValue = "1") Integer week){
        week = week < 1 ? 1: week;
        User user = userService.findByEmail(principal.getName()).orElseThrow(() -> new UserNotFoundException(principal.getName()));
        model.addAttribute("lessonsByWeek",
                    courseService.findScheduleForWeek(week, user.getUserId()));
        model.addAttribute("week", week);
        model.addAttribute("user", user);
        return "schedule";
    }
}
