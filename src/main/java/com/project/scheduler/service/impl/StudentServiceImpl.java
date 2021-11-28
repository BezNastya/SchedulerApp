package com.project.scheduler.service.impl;

import com.project.scheduler.entity.GroupCourse;
import com.project.scheduler.entity.Lesson;
import com.project.scheduler.entity.Student;
import com.project.scheduler.repository.GroupCourseRepository;
import com.project.scheduler.repository.LessonRepository;
import com.project.scheduler.repository.StudentRepository;
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

    public StudentServiceImpl(StudentRepository studentRepository,
                              GroupCourseRepository groupCourseRepository,
                              LessonRepository lessonRepository) {
        this.studentRepository = studentRepository;
        this.groupCourseRepository = groupCourseRepository;
        this.lessonRepository = lessonRepository;
    }

    @Autowired
    public void setStudentRepository(StudentRepository studentRepository){
        this.studentRepository = studentRepository;
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
    public List<Lesson> findLessonsByStudent(Student student) {
        List<GroupCourse> groupCourseList =
                groupCourseRepository.findGroupCoursesByStudentId(student.getUserId());
        List<Lesson> allLessonsList = new ArrayList<>();
        groupCourseList.forEach((groupCourse) ->
                allLessonsList.addAll(lessonRepository.findLessonsByGroupCourse(groupCourse)));
        return allLessonsList;
    }

    @Override
    public List<List<Lesson>> findLessonsByWeek(int week, Student student) {
        List<Lesson> allLessonsList = findLessonsByStudent(student);
        List<Lesson> allLessonsByWeek = new ArrayList<>();
        List<List<Lesson>> allByWeekSorted = new ArrayList<>();
        allLessonsList.forEach((lesson) -> {
            if (lesson.getDate().getWeek() == week) allLessonsByWeek.add(lesson);
        });

        sortC(allLessonsByWeek, true);

        IntStream.range(1, 7).forEach(i -> {
            List<Lesson> temp = new ArrayList<>();
            for (Lesson lesson : allLessonsList) {
                if (lesson.getDate().getDayOfTheWeek() == i) temp.add(lesson);
            }
            sortC(temp, false);
            allByWeekSorted.add(temp);
        });
        return allByWeekSorted;
    }

    private void sortC(List<Lesson> list, boolean byWeek) {
        Lesson temp;
        boolean sorted = false;

        if (byWeek)
            while (!sorted) {
                sorted = true;
                for (int i = 0; i < list.size()-1; i++) {
                    if (list.get(i).getDate().getDayOfTheWeek() > (list.get(i + 1).getDate().getDayOfTheWeek())) {
                        temp = list.get(i);
                        list.set(i, list.get(i + 1));
                        list.set(i + 1, temp);
                        sorted = false;
                    }
                }
            }
        else
            while (!sorted) {
                sorted = true;
                for (int i = 0; i < list.size()-1; i++) {
                    if (list.get(i).getDate().getLessonOrder() > (list.get(i + 1).getDate().getLessonOrder())) {
                        temp = list.get(i);
                        list.set(i, list.get(i + 1));
                        list.set(i + 1, temp);
                        sorted = false;
                    }
                }
            }
    }
}
