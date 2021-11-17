package com.project.scheduler.service.impl;

import com.project.scheduler.entity.User;
import com.project.scheduler.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;

    @Autowired
    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String email){
        User user = userRepository.findByEmail(email);

        if (user != null) {
            Set<GrantedAuthority> authorities = new HashSet<>();
            if (Objects.equals(email, "admin"))
                authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
            else if (Objects.equals(email, "teacher"))
                authorities.add(new SimpleGrantedAuthority("ROLE_TEACHER"));
            else
                authorities.add(new SimpleGrantedAuthority("ROLE_STUDENT"));

            return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), authorities);
        } else
            throw new UsernameNotFoundException("User " + email + " not found!");
    }
}
