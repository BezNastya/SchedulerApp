package com.project.scheduler.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.header.HeaderWriterFilter;

import static com.project.scheduler.entity.Role.*;


@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserDetailsServiceImpl userDetailsService;

    @Autowired
    public WebSecurityConfig(UserDetailsServiceImpl userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //CSRF disabled
        http
                .authorizeRequests()
                .antMatchers("/").authenticated()
                .antMatchers("/public/**").permitAll()
                .antMatchers("/register").permitAll()
                .antMatchers("/course/**").hasAuthority(ADMIN.getRoleStringRepresentation())
                .antMatchers("/user").authenticated()
                .antMatchers("/h2-console/**").permitAll()
                .antMatchers("/requests").hasAnyAuthority(ADMIN.getRoleStringRepresentation(), TEACHER.getRoleStringRepresentation())
                .antMatchers("/requests/approve").hasAuthority(ADMIN.getRoleStringRepresentation())
                .antMatchers("/requests/decline").hasAuthority(ADMIN.getRoleStringRepresentation())
                .antMatchers("/requests/delete").hasAuthority(TEACHER.getRoleStringRepresentation())
                .antMatchers("/admin/**").hasAuthority(ADMIN.getRoleStringRepresentation())
                .antMatchers("/teacher/**").hasAuthority(TEACHER.getRoleStringRepresentation())
                .antMatchers("/postponeLesson/**").hasAuthority(TEACHER.getRoleStringRepresentation())
                .antMatchers("/student/**").hasAuthority(STUDENT.getRoleStringRepresentation())
                .antMatchers("/my-lessons").hasAnyAuthority(TEACHER.getRoleStringRepresentation(), STUDENT.getRoleStringRepresentation())
                .antMatchers("/admin-lessons").hasAuthority("ADMIN")

                .and().formLogin().permitAll()
                .and().logout().permitAll();
        http.csrf().disable();
        http.headers().frameOptions().disable();
        http.addFilterAfter(new CustomFilter(), HeaderWriterFilter.class);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService);
    }

    /*
    @Bean
    @Override
    public UserDetailsService userDetailsService() {
        return userDetailsService;
    }
*/
    /*
    @Bean
    @Override
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }
*/


    /*
    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers("/webjars/**", "/js/**","/error/**"
                , "/css/**","/fonts/**","/libs/**","/img/**","/h2-console/**");
    }
    */


}