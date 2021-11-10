package com.project.scheduler.service.impl;

import com.project.scheduler.StartupData;
import com.project.scheduler.entity.Teacher;
import com.project.scheduler.exceptions.UserNotFoundException;
import com.project.scheduler.repository.TeacherRepository;
import com.project.scheduler.service.TeacherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TeacherServiceImpl implements TeacherService {

    private final Logger logger = LoggerFactory.getLogger(StartupData.class);
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
    public Optional<Teacher> findById(long id) {
        return teacherRepository.findById(id);
    }

    @Override
    public Teacher save(Teacher teacher) {
        logger.warn("Saving teacher");
        return teacherRepository.save(teacher);
    }

    @Override
    public List<Teacher> findAll() {
        return teacherRepository.findAll();
    }

    @Override
    public void delete(final long id) {
        teacherRepository.findById(id).orElseThrow(
                () -> new UserNotFoundException(id));
        teacherRepository.deleteById(id);
    }

    @Override
    public void updateAcademicDegree(final long id, final String academicDegree) {
        teacherRepository.updateAcademicDegree(id, academicDegree);
    }

    @Override
    public void updateDepartment(final long id, final String department) {
        teacherRepository.updateDepartment(id, department);
    }
}
