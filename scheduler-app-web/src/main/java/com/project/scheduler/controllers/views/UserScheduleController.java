package com.project.scheduler.controllers.views;

import com.project.scheduler.entity.User;
import com.project.scheduler.exceptions.UserNotFoundException;
import com.project.scheduler.service.CourseService;
import com.project.scheduler.service.ExcelService;
import com.project.scheduler.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

@Controller
public class UserScheduleController {

    public static final int FIRST_WEEK = 1;
    public static final int LAST_WEEK = 15;

    private final CourseService courseService;
    private final UserService userService;
    private final ExcelService excelService;

    Logger logger = LoggerFactory.getLogger(UserScheduleController.class);

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
        logger.warn("Downloading lessons for week {}", week);
        if (week < FIRST_WEEK || week > LAST_WEEK) {
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
        logger.warn("Retrieving lessons for week {}", week);
        week = week < 1 ? 1: week;
        User user = userService.findByEmail(principal.getName()).orElseThrow(() -> new UserNotFoundException(principal.getName()));
        model.addAttribute("lessonsByWeek",
                    courseService.findScheduleForWeek(week, user.getUserId()));
        model.addAttribute("week", week);
        model.addAttribute("user", user);
        return "schedule";
    }
}
