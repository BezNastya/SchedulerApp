package com.project.scheduler.service.impl;

import com.project.scheduler.entity.Teacher;
import com.project.scheduler.repository.TeacherRepository;
import com.project.scheduler.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeacherServiceImpl implements TeacherService {

    private final TeacherRepository teacherRepository;

//    private ScheduleService scheduleService;

    @Autowired
    public TeacherServiceImpl(TeacherRepository teacherRepository) {
        this.teacherRepository = teacherRepository;
    }

//    @Autowired
//    public void setScheduleService(ScheduleService scheduleService) {
//        this.scheduleService = scheduleService;
//    }

    @Override
    public Teacher findById(long id) {
        return teacherRepository.findById(id).orElse(null);
    }

    /*
    @Override
    public Teacher findByEmail(String email) {
        return null;
    }

    @Override
    public Teacher findByLastName(String lastName) {
        return null;
    }

    @Override
    public PostponeLesson postponeLesson(String description, Lesson canceledLesson,
                                         ScheduleDate newDate, String newPlace){
        //Request updateCourse lesson using old lesson + newDate and newPlace
        return null;
    }

    @Override
    public Schedule getTeacherLessons(long id){
        return null;
    }
    */

    @Override
    public Teacher save(Teacher teacher) {
        return teacherRepository.save(teacher);
    }

    @Override
    public List<Teacher> findAll() {
        return teacherRepository.findAll();
    }

    @Override
    public void delete(Teacher teacher) {
        teacherRepository.delete(teacher);
    }

    @Override
    public void updateAcademicDegree(final Teacher teacher, final String academicDegree) {
        teacherRepository.updateAcademicDegree(teacher.getUserId(), academicDegree);
    }

    @Override
    public void updateDepartment(final Teacher teacher, final String department) {
        teacherRepository.updateDepartment(teacher.getUserId(), department);
    }
}
