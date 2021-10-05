package com.project.scheduler.service;

import com.project.scheduler.entity.Course;
import com.project.scheduler.entity.Lesson;
import com.project.scheduler.entity.Teacher;

import java.util.List;

public interface ScheduleService {

    List<Lesson> findAllLessonsByCourse(final Course course);
    List<Lesson> findLessonsByCourseAndGroup(final  Course course, final int group);
    List<Lesson> findLessonsByTeacher(final Teacher teacher);


}
