package com.project.scheduler.service;

import com.project.scheduler.entity.Admin;

public interface AdminService {
    void save();
    Admin findByEmail(String email);
    Admin findById(long id);
}
