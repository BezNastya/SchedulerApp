package com.project.scheduler.controllers;

import com.project.scheduler.entity.Course;
import com.project.scheduler.entity.GroupCourse;
import com.project.scheduler.exceptions.CourseNotFoundException;
import com.project.scheduler.service.CourseService;
import io.swagger.v3.oas.annotations.Operation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/courses")
public class CourseController {

    Logger logger = LoggerFactory.getLogger(StudentController.class);
    Marker myMarker = MarkerFactory.getMarker("StudentClassMarker");

    private final CourseService courseService;

    @Autowired
    public CourseController(CourseService courseService){
        this.courseService = courseService;
    }

    @Operation(summary = "Get all the courses")
    @GetMapping
    public List<Course> getAllCourses(){
        logger.info(myMarker, "Getting all courses");
        return courseService.findAll();
    }

    @Operation(summary = "Get the student with the specified id")
    @GetMapping("/{id}")
    public Course getCourseById(@PathVariable Long id){
        logger.info(myMarker, "Getting course with id {}", id);
        return courseService.findCourseById(id)
                .orElseThrow(() -> new CourseNotFoundException(id));
    }

    @Operation(summary = "Get all the groups of the specified course")
    @GetMapping("/{id}/groups")
    public List<GroupCourse> getAllGroupsForCourse(@PathVariable Long id){
        Course course = courseService.findCourseById(id)
                .orElseThrow(() -> new CourseNotFoundException(id));
        logger.info(myMarker, "Getting allGroups for course {}", course);
        return courseService.findAllGroupsForCourse(course);
    }

    @Operation(summary = "Add a new course")
    @PostMapping
    public Course addCourse(@RequestBody @Valid Course course){
        logger.info(myMarker, "Adding course {}", course);
        return courseService.saveCourse(course);
    }

    @Operation(summary = "Add a few groups to the course")
    @PostMapping("{id}/groups")
    public List<GroupCourse> addGroupsToCourse(@PathVariable Long id, @RequestParam byte numberOfGroups){
        Course course = courseService.findCourseById(id)
                .orElseThrow(() -> new CourseNotFoundException(id));
        logger.info(myMarker, "Adding {} groups for course {}", numberOfGroups, course);
        return courseService.saveGroupsForCourse(course, numberOfGroups);
    }

    @Operation(summary = "Update the name of the course")
    @PutMapping("/{id}")
    public void updateCourseName(@PathVariable Long id, @RequestParam String newName){
        courseService.findCourseById(id)
                .orElseThrow(() -> new CourseNotFoundException(id));
        logger.info(myMarker, "Updating course name to {} for course with id {}", newName, id);
        courseService.updateCourseName(newName, id);
    }

    @Operation(summary = "Remove all the existing groups and add new ones")
    @PutMapping("/{id}/groups")
    public void updateNumberOfGroups(@PathVariable Long id, @RequestParam byte newNumberOfGroups){
        Course course = courseService.findCourseById(id)
                .orElseThrow(() -> new CourseNotFoundException(id));
        List<GroupCourse> groups = courseService.findAllGroupsForCourse(course);
        for (GroupCourse groupCourse : groups){
            courseService.deleteGroupById(groupCourse.getId());
        }
        courseService.saveGroupsForCourse(course, newNumberOfGroups);
        logger.info(myMarker, "Updating group number to {} for course {}", newNumberOfGroups, course);
    }

    @Operation(summary = "Delete the specified course")
    @DeleteMapping("/{id}")
    public void deleteCourse(@PathVariable Long id){
        courseService.findCourseById(id)
                .orElseThrow(() -> new CourseNotFoundException(id));
        logger.info(myMarker, "Deleting course with id{}", id);
        courseService.deleteCourseById(id);
    }

    @Operation(summary = "Delete all the groups of this course")
    @DeleteMapping("{id}/groups")
    public void deleteGroupsForCourse(@PathVariable Long id){
        Course course = courseService.findCourseById(id)
                .orElseThrow(() -> new CourseNotFoundException(id));
        courseService.deleteAllGroups(course);
        logger.info(myMarker, "Deleting groups for course {}", course);
    }

}
