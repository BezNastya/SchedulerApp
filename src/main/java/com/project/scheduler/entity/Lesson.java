package com.project.scheduler.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
public class Lesson {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long lessonId;

    @Enumerated(EnumType.STRING)
    LessonType type;

    String place;

    @Embedded
    ScheduleDate date;

    @ManyToOne
    @JoinColumn
    private GroupCourse groupCourse;

    public Lesson(LessonType type, String place, ScheduleDate date, GroupCourse groupCourse){
        this.type = type;
        this.place = place;
        this.date = date;
        this.groupCourse = groupCourse;
    }
}
