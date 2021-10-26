package com.project.scheduler.service;

import com.project.scheduler.entity.Course;
import com.project.scheduler.entity.GroupCourse;
import com.project.scheduler.entity.Student;

import java.util.List;
import java.util.Optional;

public interface CourseService {
    Course saveCourse(Course course);
    void deleteCourseById(Long courseId);
    void updateCourseName(String newName, Long toUpdateId);
    Optional<Course> findCourseById(long id);
    List<Course> findAll();
    List<GroupCourse> findAllGroupsForCourse(Course course);
    GroupCourse findGroupById(Long id);
    List<GroupCourse> saveGroupsForCourse(Course course, byte numberOfGroups);
    void deleteGroupById(Long groupId);
    void deleteAllGroups(Course course);
//    List<Course> findAllByStudent(Student student);
}
