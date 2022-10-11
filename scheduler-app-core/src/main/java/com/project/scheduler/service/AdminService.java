package com.project.scheduler.service;

import com.project.scheduler.entity.Admin;
import com.project.scheduler.repository.AdminRepository;
import com.project.scheduler.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AdminService {

    private final AdminRepository adminRepository;

    @Autowired
    public AdminService(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }

    
    public Admin save(Admin admin) {
        return adminRepository.save(admin);
    }

    
    public Optional<Admin> findByEmail(String email) {
        return adminRepository.findByEmail(email);
    }

    
    public Optional<Admin> findById(Long id) {
        return adminRepository.findById(id);
    }

    
    public List<Admin> findAll() {
        return adminRepository.findAll();
    }

    
    public void delete(Admin admin) {
        adminRepository.delete(admin);
    }

    
    public void update(Admin admin) {
        adminRepository.save(admin);
    }
}
