package com.project.scheduler.entity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

enum LessonType {
    LECTURE,
    SEMINAR,
    PRACTICE,
    LAB
}

@Component
public class Lesson {

    @Enumerated(EnumType.STRING)
    LessonType type;
    //Teacher teacher;
    String place;
    //@Autowired
    ScheduleDate date;
}
