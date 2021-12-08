package com.project.scheduler.controllers;

import com.project.scheduler.entity.Course;
import com.project.scheduler.service.CourseService;
import io.swagger.v3.oas.annotations.Operation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@Controller
public class CourseController {

    Logger logger = LoggerFactory.getLogger(StudentController.class);

    private final CourseService courseService;

    @Autowired
    public CourseController(CourseService courseService){
        this.courseService = courseService;
    }

    @Operation(summary = "New course view")
    @GetMapping("/course")
    public String course(Model model){
        List<Course> courses = courseService.findAll();
        model.addAttribute("courses", courses);
        return "course";
    }

    @Operation(summary = "Add new course")
    @PostMapping("/course/add")
    public String addCourse(@RequestParam("courseName") String courseName,
                            @RequestParam("grNum") String grNum) {
        Course course = new Course();
        course.setName(courseName);
        courseService.saveCourse(course);
        courseService.saveGroupsForCourse(course, (byte) Integer.parseInt(grNum));
        logger.info("Added course with name{}", courseName);
        return "redirect:/course";
    }

    @Operation(summary = "Delete the specified course")
    @GetMapping("/course/delete")
    public String deleteCourse(@RequestParam("id") Long id) {
        logger.info("Deleting course with id{}", id);
        Optional<Course> courseOptional = courseService.findCourseById(id);
        if (courseOptional.isPresent()) {
            Course course = courseOptional.get();
            courseService.deleteLessonsByGroupCourse_Course(course);
            courseService.deleteGroupCoursesByCourse(course);
            courseService.deleteCourseById(id);
        }
        return "redirect:/course";
    }

//    @Operation(summary = "Get all the courses")
//    @GetMapping
//    public List<Course> getAllCourses(){
//        logger.info("Getting all courses");
//        return courseService.findAll();
//    }
//
//    @Operation(summary = "Get the student with the specified id")
//    @GetMapping("/{id}")
//    public Course getCourseById(@PathVariable Long id){
//        logger.info("Getting course with id {}", id);
//        return courseService.findCourseById(id)
//                .orElseThrow(() -> new CourseNotFoundException(id));
//    }
//
//    @Operation(summary = "Get all the groups of the specified course")
//    @GetMapping("/{id}/groups")
//    public Set<GroupCourse> getAllGroupsForCourse(@PathVariable Long id){
//        Course course = courseService.findCourseById(id)
//                .orElseThrow(() -> new CourseNotFoundException(id));
//        logger.info("Getting allGroups for course {}", course);
//        return courseService.findAllGroupsForCourse(course);
//    }
//
//
//
//    @Operation(summary = "Add a few groups to the course")
//    @PostMapping("{id}/groups")
//    public Course addGroupsToCourse(@PathVariable Long id, @RequestParam byte numberOfGroups){
//        Course course = courseService.findCourseById(id)
//                .orElseThrow(() -> new CourseNotFoundException(id));
//        logger.info("Adding {} groups for course {}", numberOfGroups, course);
//        return courseService.saveGroupsForCourse(course, numberOfGroups);
//    }
//
//    @Operation(summary = "Update the name of the course")
//    @PutMapping("/{id}")
//    public void updateCourseName(@PathVariable Long id, @RequestParam String newName){
//        courseService.findCourseById(id)
//                .orElseThrow(() -> new CourseNotFoundException(id));
//        logger.info("Updating course name to {} for course with id {}", newName, id);
//        courseService.updateCourseName(newName, id);
//    }
//
//    @Operation(summary = "Remove all the existing groups and add new ones")
//    @PutMapping("/{id}/groups")
//    public void updateNumberOfGroups(@PathVariable Long id, @RequestParam byte newNumberOfGroups){
//        Course course = courseService.findCourseById(id)
//                .orElseThrow(() -> new CourseNotFoundException(id));
//        Set<GroupCourse> groups = courseService.findAllGroupsForCourse(course);
//        for (GroupCourse groupCourse : groups){
//            courseService.deleteGroupById(groupCourse.getId());
//        }
//        courseService.saveGroupsForCourse(course, newNumberOfGroups);
//        logger.info("Updating group number to {} for course {}", newNumberOfGroups, course);
//    }
//
//    @Operation(summary = "Delete the specified course")
//    @DeleteMapping("/{id}")
//    public void deleteCourse(@PathVariable Long id){
//        courseService.findCourseById(id)
//                .orElseThrow(() -> new CourseNotFoundException(id));
//        logger.info("Deleting course with id{}", id);
//        courseService.deleteCourseById(id);
//    }
//
//    @Operation(summary = "Delete all the groups of this course")
//    @DeleteMapping("{id}/groups")
//    public void deleteGroupsForCourse(@PathVariable Long id){
//        Course course = courseService.findCourseById(id)
//                .orElseThrow(() -> new CourseNotFoundException(id));
//        courseService.deleteAllGroups(course);
//        logger.info("Deleting groups for course {}", course);
//    }
//
//
//    @Operation(summary = "Get all groupCourses by student")
//    @GetMapping("/{id}/group")
//    public List<GroupCourse> findAllGroupCourseByStudent(@PathVariable Long id){
//        return courseService.findGroupCoursesByEducationUserId(id);
//    }

}
