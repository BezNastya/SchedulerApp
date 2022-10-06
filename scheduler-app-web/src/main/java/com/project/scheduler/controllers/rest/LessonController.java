package com.project.scheduler.controllers.rest;


import com.project.scheduler.entity.Lesson;
import com.project.scheduler.entity.ScheduleDate;
import com.project.scheduler.entity.WeekDay;
import com.project.scheduler.repository.GroupCourseRepository;
import com.project.scheduler.repository.LessonRepository;
import com.project.scheduler.service.CourseService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

@RestController
public class LessonController {

    private final CourseService courseService;

    @Autowired
    private GroupCourseRepository groupCourseRepository;

    @Autowired
    private LessonRepository lessonRepository;

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
    public List<Lesson> findLessons() {
        return courseService.findAllLessons();
    }

    @Operation(summary = "Get all lessons")
    @GetMapping("/lesson")
    public ScheduleDate s() {
        return courseService.findAllLessons().get(0).getDate();
    }

    @GetMapping("/lesson/{id}")
    public void removeLesson(@PathVariable long id) {
        lessonRepository.deleteById(id);
    }

}
