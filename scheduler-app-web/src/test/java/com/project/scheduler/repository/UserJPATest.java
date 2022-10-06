package com.project.scheduler.repository;

import com.project.scheduler.entity.Role;
import com.project.scheduler.entity.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@DataJpaTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UserJPATest {

    @Autowired
    UserRepository userRepository;

    private static final List<User> USERS = Arrays.asList(
            new User("email@e.com", "111", "Admin", "Admin",Role.ADMIN),
            new User("email@e.com", "111", "User", "Admin",Role.STUDENT),
            new User("email@e.com", "111", "Teacher", "Admin",Role.TEACHER));

    @BeforeAll
    void saveAdmins(){
        userRepository.saveAll(USERS);
    }

    @Test
    void shouldReturnTrueWhenRepoIsNotEmpty() {
        List<User> users = userRepository.findAll();
        assertNotNull(users);
    }

    @Test
    void shouldReturnTrueWhenRepoIsEmpty() {
        List<User> users = any();
        assertNull(users);
    }

    @Test
    void shouldReturnTrueIfUserIsAdmin(){
        User user=userRepository.findAll().get(0);
        Assertions.assertEquals(user.getRole(),Role.ADMIN);
    }

    @Test
    void shouldReturnTrueIfPasswordHasOnlyDigits(){
        String regex = "[0-9]+";
        User user=userRepository.findAll().get(2);
        Assertions.assertTrue(user.getPassword().matches(regex));
    }
    @Test
    void shouldReturnTrueIfRepositoryLessThan50(){
        assertTrue(userRepository.findAll().size()<50);
    }

}


