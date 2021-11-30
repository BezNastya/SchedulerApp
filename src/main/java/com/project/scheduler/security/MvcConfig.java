package com.project.scheduler.security;

import com.project.scheduler.entity.PostponeLesson;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.validation.Valid;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

    /*
    @PostMapping("/postponeLesson")
    public String checkPostponeSubmit(@Valid PostponeLesson postponeLesson, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "form";
        }

        return "redirect:/results";
    }
    */
}
