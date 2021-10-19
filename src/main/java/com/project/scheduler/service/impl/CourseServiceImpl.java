package com.project.scheduler.service.impl;

import com.project.scheduler.entity.Course;
import com.project.scheduler.entity.GroupCourse;
import com.project.scheduler.entity.Student;
import com.project.scheduler.repository.CourseRepository;
import com.project.scheduler.repository.GroupCourseRepository;
import com.project.scheduler.service.CourseService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CourseServiceImpl implements CourseService {

    @Autowired
    CourseRepository courseRepository;
    GroupCourseRepository groupRepository;


    @Override
    public void save(Course course) {
        courseRepository.save(course);
    }

    @Override
    public void delete(Course course) {
courseRepository.delete(course);
    }

    @Override
    public void update(Course course) {
        save(course);
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
    public List<Course> findAllByStudent(Student student) {
        return new ArrayList<>();
    }

    @Override
    public List<GroupCourse> findAllGroups() {
        return groupRepository.findAll();
    }

    @Override
    public GroupCourse findGroupById(Long id) {
        return groupRepository.findById(id).orElse(null);
    }

    @Override
    public GroupCourse saveGroup(GroupCourse group) {
        return groupRepository.save(group);
    }

    @Override
    public boolean updateGroupNum(byte groupNum, Long toUpdate) {
        GroupCourse groupUpdate = findGroupById(toUpdate);
        if (toUpdate == null){
            return false;
        }
        groupUpdate.setGroup_num(groupNum);
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
