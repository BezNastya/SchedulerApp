package com.project.scheduler.entity;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.*;

enum LessonType {
    LECTURE,
    SEMINAR,
    PRACTICE,
    LAB
}

@Data
@Entity
@Table(name = "lesson")
public class Lesson {

    @Column(name = "id")
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "lessonType")
    @Enumerated(EnumType.STRING)
    LessonType type;

//    //TODO link with teacher
//    @Column(name = "teacher")
//    Teacher teacher;

    @Column(name = "place")
    String place;
    //@Autowired

//    //TODO link with date. maybe change date on local date
//    @Column(name = "date")
//    ScheduleDate date;
}
