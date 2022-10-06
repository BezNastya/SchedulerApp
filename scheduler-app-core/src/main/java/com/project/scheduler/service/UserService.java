package com.project.scheduler.service;

import com.project.scheduler.entity.User;

import java.util.List;
import java.util.Optional;


public interface UserService {
//    boolean login(String username, String password);
    User save(User user);
    void delete(User user);
    User update(User user);
    Optional<User> findById(long id);
    List<User> findAll();
    Optional<User> findByEmail(String email);

}
