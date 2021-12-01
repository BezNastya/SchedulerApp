package com.project.scheduler.service.impl;

import com.project.scheduler.entity.GroupCourse;
import com.project.scheduler.entity.Lesson;
import com.project.scheduler.entity.Student;
import com.project.scheduler.repository.GroupCourseRepository;
import com.project.scheduler.repository.LessonRepository;
import com.project.scheduler.repository.StudentRepository;
import com.project.scheduler.service.CourseService;
import com.project.scheduler.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

@Service
public class StudentServiceImpl implements StudentService {
    private StudentRepository studentRepository;
    private GroupCourseRepository groupCourseRepository;
    private LessonRepository lessonRepository;
    private CourseService courseService;

    @Autowired
    public StudentServiceImpl(StudentRepository studentRepository,
                              GroupCourseRepository groupCourseRepository,
                              LessonRepository lessonRepository,
                              CourseService courseService) {
        this.studentRepository = studentRepository;
        this.groupCourseRepository = groupCourseRepository;
        this.lessonRepository = lessonRepository;
        this.courseService = courseService;
    }

    @Override
    public Optional<Student> findByEmail(String email) {
        return studentRepository.findByEmail(email);
    }

    @Override
    public Student save(Student s) {
        return studentRepository.save(s);
    }

    /*
        @Override
        public Student findByLastName(String username) {
            return null;
        }

        @Override
        public Student findByEmail(String email) {
            return null;
        }
    */
    @Override
    public Student findById(long id) {
        return studentRepository.findById(id).orElse(null);
    }

    @Override
    public List<Student> findAll() {
        return studentRepository.findAll();
    }

    @Override
    public void delete(Student s) {
        studentRepository.delete(s);
    }
    /*
    @Override
    public void joinCourse(Course course,Student student) {
//        student.setCourse(course);
    }

     */

    @Override
    public void updateFaculty(final Student student, final String faculty) {
        studentRepository.updateFaculty(student.getUserId(), faculty);
    }

    @Override
    public void updateSpeciality(final Student student, final String speciality) {
        studentRepository.updateSpeciality(student.getUserId(), speciality);
    }

    @Override
    public void updateTicketNumber(final Student student, final String ticketNumber) {
        studentRepository.updateTicketNumber(student.getUserId(), ticketNumber);
    }


    @Override
    public List<Lesson> findLessonsByStudent(long studentId) {
        List<GroupCourse> groupCourseList =
                groupCourseRepository.findGroupCoursesByStudentId(studentId);
        List<Lesson> allLessonsList = new ArrayList<>();
        groupCourseList.forEach((groupCourse) ->
                allLessonsList.addAll(lessonRepository.findLessonsByGroupCourse(groupCourse)));
        return allLessonsList;
    }

    @Override
    public List<List<Lesson>> findLessonsByWeekStudent(int week, long studentId) {
        List<Lesson> allLessonsList = findLessonsByStudent(studentId);
        return courseService.findLessonsByWeek(week, allLessonsList);
    }
}
