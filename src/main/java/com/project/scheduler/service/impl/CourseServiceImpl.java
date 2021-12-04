package com.project.scheduler.service.impl;

import com.project.scheduler.advice.TrackExecutionTime;
import com.project.scheduler.advice.TrackParameters;
import com.project.scheduler.entity.Course;
import com.project.scheduler.entity.GroupCourse;
import com.project.scheduler.entity.Lesson;
import com.project.scheduler.exceptions.CourseNotFoundException;
import com.project.scheduler.repository.CourseRepository;
import com.project.scheduler.repository.GroupCourseRepository;
import com.project.scheduler.repository.LessonRepository;
import com.project.scheduler.service.CourseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
@Slf4j
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;
    private final GroupCourseRepository groupRepository;
    private final LessonRepository lessonRepository;

    @Autowired
    public CourseServiceImpl(CourseRepository courseRepository, GroupCourseRepository groupCourseRepository, LessonRepository lessonRepository) {
        this.courseRepository = courseRepository;
        this.groupRepository = groupCourseRepository;
        this.lessonRepository = lessonRepository;
    }

    @Override
    public Course saveCourse(Course course) {
        return courseRepository.save(course);
    }

    @Override
    public void deleteCourseById(Long courseId) {
courseRepository.deleteById(courseId);
    }

    @Override
    public void updateCourseName(String newName, Long toUpdateId) {
        Course courseToUpdate = findCourseById(toUpdateId)
                .orElseThrow(() -> new CourseNotFoundException(toUpdateId));
        courseToUpdate.setName(newName);
        courseRepository.save(courseToUpdate);
    }

    @Override
    public Optional<Course> findCourseById(long id) {
        return courseRepository.findById(id);
    }

    @Override
    public Optional<Course> findCourseByName(String name) {
        return courseRepository.findCourseByName(name);
    }

    @Override
    public List<Course> findAll() {
        return courseRepository.findAll();
    }

    @Override
    public Set<GroupCourse> findAllGroupsForCourse(Course course) {
        return groupRepository.findAllGroupsCourseByCourse(course)
                .stream()
                .collect(Collectors.toSet());
    }

    @Override
    public GroupCourse findGroupById(Long id) {
        return groupRepository.findById(id).orElse(null);
    }


    @Override
    public Course saveGroupsForCourse(Course course, byte numberOfGroups) {
        Set<GroupCourse> res = new HashSet<>();
        for (byte i = 1; i <= numberOfGroups; i++){
            GroupCourse group = new GroupCourse(i);
            group.setCourse(course);
            group.setStudents(new HashSet<>());
            group.setTeachers(new HashSet<>());
            res.add(group);
            groupRepository.save(group);
        }
        course.setGroups(res);
        return courseRepository.save(course);
    }

    @Override
    public void deleteGroupById(Long groupId) {
        groupRepository.deleteById(groupId);
    }

    @Override
    public void deleteAllGroups(Course course) {
        Set<GroupCourse> groups = findAllGroupsForCourse(course);
        for (GroupCourse group : groups){
            deleteGroupById(group.getId());
        }
    }

    @Override @TrackParameters
    public List<GroupCourse> findGroupCoursesByStudentId(Long id) {
        return groupRepository.findGroupCoursesByStudentId(id);
    }

    @Override
    public List<GroupCourse> findGroupCoursesByTeacherId(Long id) {
        return groupRepository.findGroupCoursesByTeacherId(id);
    }

    @Override
    @TrackParameters
    public List<Lesson> findLessonsByGroupCourse(GroupCourse groupCourse) {
        return lessonRepository.findLessonsByGroupCourse(groupCourse);
    }

    @Override
    @TrackExecutionTime
    public List<Lesson> findAllLessons() {
        return lessonRepository.findAll();
    }

    @Override
    public List<List<Lesson>> findLessonsByWeek(int week, List<Lesson> lessons){
        List<Lesson> allLessonsByWeek = new ArrayList<>();
        List<List<Lesson>> allByWeekSorted = new ArrayList<>();
        lessons.forEach((lesson) -> {
            if (lesson.getDate().getWeek() == week) allLessonsByWeek.add(lesson);
        });

        sortC(allLessonsByWeek, true);

        IntStream.range(1, 7).forEach(i -> {
            List<Lesson> temp = new ArrayList<>();
            for (Lesson lesson : allLessonsByWeek) {
                if (lesson.getDate().getDayOfTheWeek().ordinal() == i) temp.add(lesson);
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
                    if (list.get(i).getDate().getDayOfTheWeek().ordinal() > (list.get(i + 1).getDate().getDayOfTheWeek().ordinal())) {
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
