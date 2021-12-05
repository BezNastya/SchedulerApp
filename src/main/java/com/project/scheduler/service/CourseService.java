package com.project.scheduler.service;

import com.project.scheduler.entity.*;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Map;
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


    List<GroupCourse> findGroupCoursesByEducationUserId(Long id);
    List<Lesson> findLessonsByGroupCourse(GroupCourse groupCourse);

    List<List<Lesson>> findLessonsByWeek(final int week, final List<Lesson> lessons);

    List<List<Lesson>> findLessonsForWeekByEducationUserId(int week, long id);
    List<Lesson> findLessonsByEducationUserId(long id);

    Map<WeekDay, List<Lesson>> findScheduleForWeek(int week, long id);

    List<Lesson> findAllLessons();

    List<Course> findNotAttendedCourses(final Long id);
}
