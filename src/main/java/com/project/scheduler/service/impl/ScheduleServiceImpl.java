package com.project.scheduler.service.impl;

import com.project.scheduler.entity.Course;
import com.project.scheduler.entity.Lesson;
import com.project.scheduler.entity.Teacher;
import com.project.scheduler.service.ScheduleService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScheduleServiceImpl implements ScheduleService {

    @Override
    public List<Lesson> findAllLessonsByCourse(final Course course) {
        return null;
    }

    @Override
    public List<Lesson> findLessonsByCourseAndGroup(final Course course, final int group) {
        return null;
    }

    @Override
    public List<Lesson> findLessonsByTeacher(final Teacher teacher) {
        return null;
    }
}
