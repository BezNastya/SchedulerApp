package com.project.scheduler.service.impl;

import com.project.scheduler.entity.User;
import com.project.scheduler.repository.UserRepository;
import com.project.scheduler.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {


    private final UserRepository userRepository;
    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void save(User user) {
        userRepository.save(user);
    }

    @Override
    public void delete(User user) {
    userRepository.delete(user);
    }

    @Override
    public void update(User user) {
        save(user);
    }

    @Override
    public Optional<User> findById(long id) {
        return userRepository.findById(id);
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }
    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}
