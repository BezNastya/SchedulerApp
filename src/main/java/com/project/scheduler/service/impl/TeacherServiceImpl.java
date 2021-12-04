package com.project.scheduler.service.impl;

import com.project.scheduler.StartupData;
import com.project.scheduler.entity.*;
import com.project.scheduler.exceptions.UserNotFoundException;
import com.project.scheduler.repository.GroupCourseRepository;
import com.project.scheduler.repository.LessonRepository;
import com.project.scheduler.repository.TeacherRepository;
import com.project.scheduler.service.CourseService;
import com.project.scheduler.service.TeacherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TeacherServiceImpl implements TeacherService {

    private final Logger logger = LoggerFactory.getLogger(StartupData.class);
    private final TeacherRepository teacherRepository;
    private final GroupCourseRepository groupCourseRepository;
    private final LessonRepository lessonRepository;
    private final CourseService courseService;

    @Autowired
    public TeacherServiceImpl(TeacherRepository teacherRepository,
                              GroupCourseRepository groupCourseRepository,
                              LessonRepository lessonRepository,
                              CourseService courseService) {
        this.teacherRepository = teacherRepository;
        this.groupCourseRepository = groupCourseRepository;
        this.lessonRepository = lessonRepository;
        this.courseService = courseService;
    }

    @Override
    public Optional<Teacher> findByEmail(String email) {
        return teacherRepository.findByEmail(email);
    }

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

    @Override
    public List<Lesson> findLessonsByTeacher(Teacher teacher) {
        List<GroupCourse> groupCourseList =
                groupCourseRepository.findGroupCoursesByTeacherId(teacher.getUserId());
        List<Lesson> allLessonsList = new ArrayList<>();
        groupCourseList.forEach((groupCourse)
                -> allLessonsList.addAll(lessonRepository.findLessonsByGroupCourse(groupCourse)));
        return allLessonsList;
    }

    @Override
    public List<List<Lesson>> findLessonsByWeekTeacher(int week, Teacher teacher) {
        List<Lesson> allLessonsList = findLessonsByTeacher(teacher);
        return courseService.findLessonsByWeek(week, allLessonsList);
    }
}
