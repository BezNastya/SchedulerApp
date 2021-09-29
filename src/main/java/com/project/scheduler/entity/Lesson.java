package com.project.scheduler.entity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

enum LessonType {
    LECTURE,
    SEMINAR,
    PRACTICE,
    LAB
}

@Component
public class Lesson {

    LessonType type;
    //Teacher teacher;
    String place;
    //@Autowired
    ScheduleDate date;
}
