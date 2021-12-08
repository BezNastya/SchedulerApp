package com.project.scheduler.service.impl;

import com.project.scheduler.advice.TrackExecutionTime;
import com.project.scheduler.advice.TrackParameters;
import com.project.scheduler.entity.Course;
import com.project.scheduler.entity.GroupCourse;
import com.project.scheduler.entity.Lesson;
import com.project.scheduler.entity.WeekDay;
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
import java.util.stream.Stream;

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
        return new HashSet<>(groupRepository.findAllGroupsCourseByCourse(course));
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
    public List<GroupCourse> findGroupCoursesByEducationUserId(Long id) {
        return groupRepository.findGroupCoursesByEducationUserId(id);
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
    public List<Course> findNotAttendedCourses(Long id) {
        List<GroupCourse> groupCourses = findGroupCoursesByEducationUserId(id);
        List<Course> courses = new ArrayList<>();
        groupCourses.forEach((groupCourse -> {
            if (!courses.contains(groupCourse.getCourse()))
                courses.add(groupCourse.getCourse());
        }
        ));
        List<Course> notAtt = findAll();
        notAtt.removeAll(courses);
        return notAtt;
    }

    @Override
    public List<Lesson> findLessonsByEducationUserId(long id) {
        List<GroupCourse> groupCourseList =
                groupRepository.findGroupCoursesByEducationUserId(id);
        List<Lesson> allLessonsList = new ArrayList<>();
        groupCourseList.forEach((groupCourse) ->
                allLessonsList.addAll(lessonRepository.findLessonsByGroupCourse(groupCourse)));
        return allLessonsList;
    }

    @Override
    public List<List<Lesson>> findLessonsForWeekByEducationUserId(int week, long id) {
        List<Lesson> allLessonsList = findLessonsByEducationUserId(id);
        return findLessonsByWeek(week, allLessonsList);
    }

    public Map<WeekDay, List<Lesson>> findScheduleForWeek(int week, long id) {
        List<Lesson> lessonsList = findLessonsByEducationUserId(id).stream().filter(l -> l.getDate().getWeek() == week).collect(Collectors.toList());
        Map<WeekDay, List<Lesson>> result = new TreeMap<>();
        Stream.of(WeekDay.values()).forEach((weekDay -> result.put(weekDay, lessonsList.stream().filter(l -> l.getDate().getDayOfTheWeek() == weekDay).collect(Collectors.toList()))));
        return result;
    }

    @Override
    public List<List<Lesson>> findLessonsByWeek(int week, List<Lesson> lessons){
        List<Lesson> allLessonsByWeek = lessons.stream().filter(lesson -> lesson.getDate().getWeek() == week).collect(Collectors.toList());
        List<List<Lesson>> allByWeekSorted = new ArrayList<>();

        Stream.of(WeekDay.values()).forEach(w -> {
            List<Lesson> temp = allLessonsByWeek.stream().filter(lesson -> lesson.getDate().getDayOfTheWeek() == w).sorted().collect(Collectors.toList());
            allByWeekSorted.add(temp);
        });

        return allByWeekSorted;
    }

}
