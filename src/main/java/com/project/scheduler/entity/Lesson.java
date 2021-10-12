package com.project.scheduler.entity;

import lombok.*;

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

//    TODO link with teacher
//    @Column(name = "teacher")
//    Teacher teacher;

    String place;
    //@Autowired

//    //TODO link with date. maybe change date on local date
    @Embedded
    ScheduleDate date;

    @ManyToOne
    @JoinColumn(name = "groupCourse_id", insertable = false, updatable = false)
    private GroupCourse groupCourse;
}
