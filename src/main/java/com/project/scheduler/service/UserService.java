package com.project.scheduler.service;

import com.project.scheduler.entity.User;

import java.util.List;
import java.util.Optional;


public interface UserService {
    void save(User user);
    void delete(User user);
    void update(User user);
    Optional<User> findById(long id);
    List<User> findAll();
    User findByEmail(String email);
}
