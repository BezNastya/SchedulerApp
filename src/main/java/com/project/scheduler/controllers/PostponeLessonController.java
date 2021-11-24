package com.project.scheduler.controllers;

import com.project.scheduler.entity.PostponeLesson;
import com.project.scheduler.entity.Schedule;
import com.project.scheduler.entity.ScheduleDate;
import com.project.scheduler.service.PostponeLessonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;

@Controller
public class PostponeLessonController {

    @Autowired
    PostponeLessonService postponeLessonService;

    @GetMapping("/postponeLesson")
    public String postponeLessonForm(Model model){

        model.addAttribute("postponeLesson",new PostponeLesson());

        return "postponeLesson";
    }


    //@RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date date   - for requesting dates
    @PostMapping("/postponeLesson/submit")
    public String submitPostponeLessonForm(@RequestParam("id") long id, @RequestParam("week") int week,
                                           @RequestParam("day") int day, @RequestParam("lesson") int lesson,
                                           @ModelAttribute("postponeLesson")PostponeLesson postponeLesson) {

        ScheduleDate scheduleDate = new ScheduleDate();
        scheduleDate.setWeek(week);
        scheduleDate.setDayOfTheWeek(day);
        scheduleDate.setLessonOrder(lesson);
        postponeLessonService.postponeLesson(id, postponeLesson);
        return "/postponeLesson";
    }

}