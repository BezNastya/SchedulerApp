package com.project.scheduler.service.impl;

import com.project.scheduler.entity.Course;
import com.project.scheduler.entity.GroupCourse;
import com.project.scheduler.exceptions.CourseNotFoundException;
import com.project.scheduler.repository.CourseRepository;
import com.project.scheduler.repository.GroupCourseRepository;
import com.project.scheduler.service.CourseService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CourseServiceImpl implements CourseService {

    @Autowired
    CourseRepository courseRepository;
    GroupCourseRepository groupRepository;


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
    public List<Course> findAll() {
        return courseRepository.findAll();
    }

    @Override
    public List<GroupCourse> findAllGroupsForCourse(Course course) {
        return groupRepository.findAllGroupsCourseByCourse(course);
    }

    @Override
    public GroupCourse findGroupById(Long id) {
        return groupRepository.findById(id).orElse(null);
    }

    @Override
    public GroupCourse findGroupByNumberAndCourse(Course course, byte group_num) {
        return groupRepository.findGroupCourseByCourseAndGroupNum(course, group_num);
    }

    @Override
    public GroupCourse saveGroup(GroupCourse group) {
        return groupRepository.save(group);
    }

    @Override
    public GroupCourse saveGroupForCourse(Course course, Long groupId) {
        GroupCourse groupCourse = findGroupById(groupId);
        groupCourse.setCourse(course);
        return groupCourse;
    }

    @Override
    public boolean updateGroupNum(byte groupNum, Long toUpdate) {
        GroupCourse groupUpdate = findGroupById(toUpdate);
        if (toUpdate == null){
            return false;
        }
        groupUpdate.setGroupNum(groupNum);
        groupRepository.save(groupUpdate);
        return true;
    }

    @Override
    public void deleteGroupById(Long groupId) {
        groupRepository.deleteById(groupId);
    }

//    @Override
//    public List<Course> findAllByStudent(Student student) {
//        return new ArrayList<Course>();
//    }
}
