package com.project.scheduler.service;

import com.project.scheduler.entity.Teacher;

import java.util.List;

public interface TeacherService {
    Teacher save(Teacher teacher);
    Teacher findById(long id);
    List<Teacher> findAll();
    void delete(Teacher teacher);
    void updateAcademicDegree(final Teacher teacher, final String academicDegree);
    void updateDepartment(final Teacher teacher, final String department);
    //Teacher findByEmail(String email);
    //Teacher findByLastName(String username);
    //PostponeLesson postponeLesson(String description, Lesson canceledLesson,
              //                    ScheduleDate newDate, String newPlace);
    //Schedule getTeacherLessons(long id);
}
