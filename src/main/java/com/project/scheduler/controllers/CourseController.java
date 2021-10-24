package com.project.scheduler.controllers;

import com.project.scheduler.entity.Course;
import com.project.scheduler.entity.GroupCourse;
import com.project.scheduler.exceptions.CourseNotFoundException;
import com.project.scheduler.exceptions.GroupNotFoundException;
import com.project.scheduler.service.CourseService;
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

    @GetMapping
    public List<Course> getAllCourses(){
        logger.info(myMarker, "Getting all courses");
        return courseService.findAll();
    }

    @GetMapping("/{id}")
    public Course getCourseById(@PathVariable Long id){
        logger.info(myMarker, "Getting course with id {}", id);
        return courseService.findCourseById(id)
                .orElseThrow(() -> new CourseNotFoundException(id));
    }

    @GetMapping("/{id}/groups")
    public List<GroupCourse> getAllGroupsForCourse(@PathVariable Long id){
        Course course = courseService.findCourseById(id)
                .orElseThrow(() -> new CourseNotFoundException(id));
        logger.info(myMarker, "Getting allGroups for course {}", course);
        return courseService.findAllGroupsForCourse(course);
    }

    @GetMapping("/{courseId}/groups/{num}")
    public GroupCourse getGroupForCourseByNum(@PathVariable Long courseId, @PathVariable byte num){
        Course course = courseService.findCourseById(courseId)
                .orElseThrow(() -> new CourseNotFoundException(courseId));
        GroupCourse group = courseService.findGroupByNumberAndCourse(course, num);
        logger.info(myMarker, "Getting group with num {} for course {}", num, course);
        if (group == null)
            throw new GroupNotFoundException(course, num);
        return group;
    }

    @PostMapping
    public Course addCourse(@RequestBody @Valid Course course){
        logger.info(myMarker, "Adding course {}", course);
        return courseService.saveCourse(course);
    }

    //Something wrong
    @PostMapping("{id}/groups")
    public GroupCourse addGroupToCourse(@PathVariable Long id, @RequestBody @Valid GroupCourse groupCourse){
        Course course = courseService.findCourseById(id)
                .orElseThrow(() -> new CourseNotFoundException(id));
        logger.info(myMarker, "Adding group {} for course {}", groupCourse, course);
        courseService.saveGroup(groupCourse);
        return courseService.saveGroupForCourse(course, groupCourse.getId());
    }

    @PutMapping("/{id}")
    public void updateCourseName(@PathVariable Long id, @RequestParam String newName){
        courseService.findCourseById(id)
                .orElseThrow(() -> new CourseNotFoundException(id));
        logger.info(myMarker, "Updating course name to {} for course with id {}", newName, id);
        courseService.updateCourseName(newName, id);
    }

    //Something wrong
    @PutMapping("/{id}/groups/{num}")
    public void updateGroupNumber(@PathVariable Long id, @PathVariable byte num, @RequestParam byte newNum){
        Course course = courseService.findCourseById(id)
                .orElseThrow(() -> new CourseNotFoundException(id));
        GroupCourse group = courseService.findGroupByNumberAndCourse(course, num);
        if (group == null)
            throw new GroupNotFoundException(course, num);
        logger.info(myMarker, "Updating group {} number to {} for course {}", group, newNum, course);
        courseService.updateGroupNum(newNum, group.getId());
    }

    @DeleteMapping("/{id}")
    public void deleteCourse(@PathVariable Long id){
        courseService.findCourseById(id)
                .orElseThrow(() -> new CourseNotFoundException(id));
        logger.info(myMarker, "Deleting course with id{}", id);
        courseService.deleteCourseById(id);
    }

    @DeleteMapping("{id}/groups/{num}")
    public void deleteGroupForCourse(@PathVariable Long id, @PathVariable byte num){
        Course course = courseService.findCourseById(id)
                .orElseThrow(() -> new CourseNotFoundException(id));
        GroupCourse group = courseService.findGroupByNumberAndCourse(course, num);
        if (group == null)
            throw new GroupNotFoundException(course, num);
        logger.info(myMarker, "Deleting group {} for course {}", group, course);
        courseService.deleteGroupById(group.getId());
    }

}
