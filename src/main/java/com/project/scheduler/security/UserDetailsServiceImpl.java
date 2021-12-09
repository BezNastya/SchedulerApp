package com.project.scheduler.security;

import com.project.scheduler.entity.User;
import com.project.scheduler.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final Logger logger = LoggerFactory.getLogger(UserDetailsServiceImpl.class);

    @Autowired
    private UserRepository userRepository;

    @Cacheable(cacheNames = "emails")
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        logger.warn("Looking for the user " + email + " in the database");

        Optional<User> user = userRepository.findByEmail(email);

        if (user.isPresent())
            return new CustomUserDetails(user.get());
        else
            throw new UsernameNotFoundException("There are no users with the email: " + email);
    }
}
