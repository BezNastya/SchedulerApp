package com.project.scheduler.service;

import com.project.scheduler.entity.Course;
import com.project.scheduler.entity.GroupCourse;
import com.project.scheduler.entity.Lesson;
import com.project.scheduler.entity.Student;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface CourseService {
    Course saveCourse(Course course);
    void deleteCourseById(Long courseId);
    void updateCourseName(String newName, Long toUpdateId);
    Optional<Course> findCourseById(long id);
    Optional<Course> findCourseByName(String name);
    List<Course> findAll();
    Set<GroupCourse> findAllGroupsForCourse(Course course);
    GroupCourse findGroupById(Long id);
    Course saveGroupsForCourse(Course course, byte numberOfGroups);
    void deleteGroupById(Long groupId);
    void deleteAllGroups(Course course);
//    List<Course> findAllByStudent(Student student);


    List<GroupCourse> findGroupCoursesByStudentId(Long id);
    List<GroupCourse> findGroupCoursesByTeacherId(Long id);
    List<Lesson> findLessonsByGroupCourse(GroupCourse groupCourse);

    List<Lesson> findAllLessons();
}
