package com.project.scheduler.entity;

import lombok.Data;
import org.springframework.stereotype.Component;

import javax.persistence.*;

@Entity
@Data
@Table(name = "PostponedLessons")
public class PostponeLesson {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    //Коментарі
    @Column(name = "description")
    private String description;

    //Предмет, що перенесено
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "canceledLesson", nullable = true)
    private Lesson canceledLesson;

    //Нова дата
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "newDate", nullable = true)
    private ScheduleDate newDate;

    //Нове місце(за потреби)
    @Column(name = "newPlace")
    private String newPlace;

}
