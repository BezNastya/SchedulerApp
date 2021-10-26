package com.project.scheduler.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

enum LessonType {
    LECTURE,
    SEMINAR,
    PRACTICE,
    LAB
}

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
public class Lesson {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Enumerated(EnumType.STRING)
    LessonType type;

    String place;

    @Embedded
    ScheduleDate date;

    @ManyToOne
    @JoinColumn(name = "groupCourse_id", insertable = false, updatable = false)
    private GroupCourse groupCourse;
}
