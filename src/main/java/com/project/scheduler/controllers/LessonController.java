package com.project.scheduler.controllers;


import com.project.scheduler.entity.Lesson;
import com.project.scheduler.service.CourseService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@RestController
public class LessonController {

    private final CourseService courseService;

    @Autowired
    public LessonController(CourseService courseService) {
        this.courseService = courseService;
    }

    @Operation(summary = "Get all lessons by groupCourse")
    @GetMapping("/{id}/lesson")
    public List<Lesson> findLessonsByGroupCourse(@PathVariable Long id){
        return courseService.findLessonsByGroupCourse(courseService.findGroupById(id));
    }
    @Operation(summary = "Get all lessons")
    @GetMapping("/lessons")
    public List<Lesson> findLessons(){
        return courseService.findAllLessons();
    }
}
