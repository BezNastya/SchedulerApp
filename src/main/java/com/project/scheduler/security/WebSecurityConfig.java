package com.project.scheduler.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.header.HeaderWriterFilter;


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
                .antMatchers("/course/**").hasAuthority("ADMIN")
                .antMatchers("/user").authenticated()
                .antMatchers("/user/**").permitAll()
                .antMatchers("/h2-console/**").permitAll()
                .antMatchers("/requests").hasAnyAuthority("ADMIN", "TEACHER")
                .antMatchers("/requests/approve").hasAuthority("ADMIN")
                .antMatchers("/requests/decline").hasAuthority("ADMIN")
                .antMatchers("/requests/delete").hasAuthority("TEACHER")
                .antMatchers("/admin/**").hasAuthority("ADMIN")
                .antMatchers("/teacher/**").hasAuthority("TEACHER")
                .antMatchers("/student/**").hasAuthority("STUDENT")
                .antMatchers("/my-lessons").hasAnyAuthority("TEACHER", "STUDENT")
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