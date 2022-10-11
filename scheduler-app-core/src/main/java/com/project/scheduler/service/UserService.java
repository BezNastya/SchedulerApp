package com.project.scheduler.service;

import com.project.scheduler.entity.User;
import com.project.scheduler.repository.GroupCourseRepository;
import com.project.scheduler.repository.UserRepository;
import com.project.scheduler.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final GroupCourseRepository groupCourseRepository;
    @Autowired
    public UserService(UserRepository userRepository, GroupCourseRepository groupCourseRepository) {
        this.userRepository = userRepository;
        this.groupCourseRepository = groupCourseRepository;
    }

    
    public User save(User user) {
        return userRepository.save(user);
    }


    
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }


    
    public List<User> findAll() {
        return userRepository.findAll();
    }

    
    public void delete(User user) {
        userRepository.delete(user);
    }

    
    public User update(User user) {
        return userRepository.save(user);
    }

    
    public Optional<User> findById(long id) {
        return userRepository.findById(id);
    }
}
