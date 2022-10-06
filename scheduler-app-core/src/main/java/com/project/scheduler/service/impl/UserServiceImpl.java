package com.project.scheduler.service.impl;

import com.project.scheduler.entity.User;
import com.project.scheduler.repository.GroupCourseRepository;
import com.project.scheduler.repository.UserRepository;
import com.project.scheduler.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final GroupCourseRepository groupCourseRepository;
    @Autowired
    public UserServiceImpl(UserRepository userRepository, GroupCourseRepository groupCourseRepository) {
        this.userRepository = userRepository;
        this.groupCourseRepository = groupCourseRepository;
    }

//    @Override
//    public boolean login(String username, String password) {
//        return false;
//    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }


    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }


    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public void delete(User user) {
        userRepository.delete(user);
    }

    @Override
    public User update(User user) {
        return userRepository.save(user);
    }

    @Override
    public Optional<User> findById(long id) {
        return userRepository.findById(id);
    }
}
