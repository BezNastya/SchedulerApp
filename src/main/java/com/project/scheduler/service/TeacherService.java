package com.project.scheduler.service;

import com.project.scheduler.entity.Teacher;

import java.util.List;
import java.util.Optional;

public interface TeacherService {
    Teacher save(Teacher teacher);
    Optional<Teacher> findById(long id);
    List<Teacher> findAll();
    void delete(final long id);
    void updateAcademicDegree(final long id, final String academicDegree);
    void updateDepartment(final long id, final String department);
    //Teacher findByEmail(String email);
    //Teacher findByLastName(String username);
    //PostponeLesson postponeLesson(String description, Lesson canceledLesson,
              //                    ScheduleDate newDate, String newPlace);
    //Schedule getTeacherLessons(long id);
}
