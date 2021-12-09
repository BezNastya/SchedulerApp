package com.project.scheduler.service;

import com.project.scheduler.entity.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public interface CourseService {
    Course saveCourse(Course course);
    void updateCourseName(String newName, Long toUpdateId);
    Optional<Course> findCourseById(long id);
    Optional<Course> findCourseByName(String name);
    List<Course> findAll();
    Set<GroupCourse> findAllGroupsForCourse(Course course);
    GroupCourse findGroupById(Long id);
    Course saveGroupsForCourse(Course course, byte numberOfGroups);
    void deleteGroupById(Long groupId);
    void deleteGroupCoursesByCourse(Course course);
//    List<Course> findAllByStudent(Student student);
    void deleteCourseWithAll(Course course);


    List<GroupCourse> findGroupCoursesByEducationUserId(Long id);
    List<Lesson> findLessonsByGroupCourse(GroupCourse groupCourse);

    List<Lesson> findLessonsByEducationUserId(long id);
    List<Lesson> findLessonsByEducationUserIdForWeek(long id, int week);

    Page<Lesson> findPaginatedLessons(long id, Pageable pageable);

    Map<WeekDay, List<Lesson>> findScheduleForWeek(int week, long id);

    List<Lesson> findAllLessons();
    List<Lesson> findAllLessonsByWeek(int week);

    List<Course> findNotAttendedCourses(final Long id);


    void deleteLessonsByGroupCourse_Course(Course course);

}
