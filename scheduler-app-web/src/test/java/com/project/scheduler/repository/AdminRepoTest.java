package com.project.scheduler.repository;

import com.project.scheduler.entity.Admin;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class AdminRepoTest {

    @Autowired
    AdminRepository adminRepository;

    private static final List<Admin> ADMINS = Arrays.asList(new Admin("email@e.com", "111", "Admin", "Admin"),
            new Admin("email2@e.com", "222", "First", "Last"),
            new Admin("email3@e.com", "333", "Fff", "Bae"));

    @BeforeAll
    void saveAdmins(){
        adminRepository.saveAll(ADMINS);
    }

    @Test
    void shouldReturnAllAdmins_whenFindAll(){
        List<Admin> admins = adminRepository.findAll();
        assertEquals(admins, ADMINS);
    }

    @Test
    void shouldFindExistingAdmin_whenAdminExists(){
        Admin expected = adminRepository.findAll().get(0);
        long id = adminRepository.findAll().get(0).getUserId();
        Optional<Admin> admin = adminRepository.findById(id);
        assertTrue(admin.isPresent());
        Assertions.assertEquals(expected, admin.get());
    }

    @Test
    void shouldReturnEmptyOptional_whenNoSuchAdmin(){
        Optional<Admin> admin = adminRepository.findById((long) 10);
        assertFalse(admin.isPresent());
    }

    @Test
    void shouldSaveNewAdmin_andReturnHim(){
        Admin expected = new Admin("new@new.com", "new", "new", "new");
        Admin actual = adminRepository.save(expected);
        Assertions.assertEquals(expected, actual);
        List<Admin> admins = adminRepository.findAll();
        assertEquals(4, admins.size());
    }

    @Test
    void shouldDeleteExistingAdmin() {
        int sizeBefore = adminRepository.findAll().size();
        long id = adminRepository.findAll().get(0).getUserId();
        adminRepository.deleteById(id);
        List<Admin> admins = adminRepository.findAll();
        assertEquals(sizeBefore - 1, admins.size());
    }

    @Test
    void shouldNotDeleteAdmin_forNonExistingId(){
        assertThrows(Exception.class, () -> adminRepository.deleteById((long) 10));
        List<Admin> admins = adminRepository.findAll();
        assertEquals(ADMINS, admins);
    }
}
