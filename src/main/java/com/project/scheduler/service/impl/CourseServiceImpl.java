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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Slf4j
@Transactional
public class CourseServiceImpl implements CourseService {

    private final Logger logger = LoggerFactory.getLogger(CourseServiceImpl.class);

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
    public void deleteGroupCoursesByCourse(Course course) {
        groupRepository.deleteGroupCoursesByCourse(course);
    }

    @CacheEvict(cacheNames = "groups", allEntries = true)
    @Override
    public void deleteCourseWithAll(Course course) {
        deleteLessonsByGroupCourse_Course(course);
        findAllGroupsForCourse(course).forEach((group) -> {
            group.getStudents().forEach(student -> {
                Set<GroupCourse> t = student.getGroupCourse();
                t.remove(group);
                student.setGroupCourse(t);
            });
            group.getTeachers().forEach((teacher -> {
                Set<GroupCourse> t = teacher.getGroupCourse();
                t.remove(group);
                teacher.setGroupCourse(t);
            }));
            group.setStudents(new HashSet<>());
            group.setTeachers(new HashSet<>());
            groupRepository.save(group);
        });
        deleteGroupCoursesByCourse(course);
        courseRepository.deleteById(course.getId());
    }


    @Cacheable(cacheNames = "groups")
    @Override @TrackParameters
    public List<GroupCourse> findGroupCoursesByEducationUserId(Long id) {
        logger.warn("Getting all groups for the user with id " + id);
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
    public List<Lesson> findAllLessonsByWeek(int week) {
        return lessonRepository.findLessonsByDate_Week(week);
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
    public void deleteLessonsByGroupCourse_Course(Course course) {
        lessonRepository.deleteLessonsByGroupCourse_Course(course);
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
    public List<Lesson> findLessonsByEducationUserIdForWeek(long id, int week) {
        return findLessonsByEducationUserId(id).stream().filter(lesson -> lesson.getDate().getWeek() == week).collect(Collectors.toList());
    }

    @Override
    public Page<Lesson> findPaginatedLessons(long id, Pageable pageable) {
        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        List<Lesson> lessons = findLessonsByEducationUserIdForWeek(id, pageSize);
        return new PageImpl<>(lessons, PageRequest.of(currentPage, pageSize), lessons.size());
    }

    @Override
    public Map<WeekDay, List<Lesson>> findScheduleForWeek(int week, long id) {
        List<Lesson> lessonsList = findLessonsByEducationUserId(id).stream().filter(l -> l.getDate().getWeek() == week).collect(Collectors.toList());
        Map<WeekDay, List<Lesson>> result = new TreeMap<>();
        Stream.of(WeekDay.values()).forEach((weekDay -> result.put(weekDay, lessonsList.stream().filter(l -> l.getDate().getDayOfTheWeek() == weekDay).collect(Collectors.toList()))));
        return result;
    }

}
