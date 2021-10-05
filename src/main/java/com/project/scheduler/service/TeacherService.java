package com.project.scheduler.service;

import com.project.scheduler.entity.*;

public interface TeacherService {
    Teacher findById(long id);
    Teacher findByEmail(String email);
    Teacher findByLastName(String username);
    PostponeLesson postponeLesson(String description, Lesson canceledLesson,
                                  ScheduleDate newDate, String newPlace);
    Schedule getTeacherLessons(long id);
}
