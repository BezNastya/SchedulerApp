package com.project.scheduler.controllers;

import com.project.scheduler.entity.PostponeLesson;
import com.project.scheduler.entity.Student;
import com.project.scheduler.service.PostponeLessonService;
import com.project.scheduler.service.StudentService;
import io.swagger.v3.oas.annotations.Operation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class PostponeLessonController {


    @Autowired
    PostponeLessonService postponeLessonService;

    @GetMapping("/postponeLesson/")
    public String postponeLessonForm(Model model){

        model.addAttribute("postponeLesson",new PostponeLesson());

        return "postponeLesson";
    }

    @GetMapping("/postponeLesson/submit/")
    public String submitPostponeLessonForm(@ModelAttribute("postponeLesson")PostponeLesson postponeLesson,@PathVariable("id") long id ){

    postponeLessonService.postponeLessonTimePlaceAndDescription(id,postponeLesson.getNewDate(),postponeLesson.getDescription(),postponeLesson.getNewPlace());
        return "postponeLesson";
    }





}