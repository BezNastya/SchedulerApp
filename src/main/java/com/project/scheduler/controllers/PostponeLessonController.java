package com.project.scheduler.controllers;

import com.project.scheduler.entity.PostponeLesson;
import com.project.scheduler.entity.ScheduleDate;
import com.project.scheduler.service.PostponeLessonService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;

@Controller
public class PostponeLessonController {

    Logger logger = LoggerFactory.getLogger(PostponeLessonController.class);

    @Autowired
    PostponeLessonService postponeLessonService;

    @GetMapping("/postponeLesson")
    public String postponeLessonForm(Model model){

        model.addAttribute("postponeLesson",new PostponeLesson());

        return "postponeLesson";
    }


    //@RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date date   - for requesting dates
    @PostMapping("/postponeLesson")
    public String submitPostponeLessonForm(@RequestParam("id") long id, @RequestParam("week") int week,
                                           @RequestParam("day") int day, @RequestParam("lesson") int lesson,
                                           @ModelAttribute("postponeLesson")PostponeLesson postponeLesson) {

        logger.warn("Received submit request with id {}, week {}, day {}, lesson order {} ", id, day, week, lesson);
        ScheduleDate scheduleDate = new ScheduleDate();
        scheduleDate.setWeek(week);
        scheduleDate.setDayOfTheWeek(day);
        scheduleDate.setLessonOrder(lesson);
        postponeLesson.setNewDate(scheduleDate);
        postponeLessonService.postponeLesson(id, postponeLesson);
        return "/postponeLesson";
    }

}