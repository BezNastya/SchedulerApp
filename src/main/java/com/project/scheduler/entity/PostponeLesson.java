package com.project.scheduler.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;
import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class PostponeLesson {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    //Коментарі
    private String description = "";

    //Предмет, що перенесено
    @OneToOne
    @JoinColumn(name = "canceledLesson", nullable = false)
    @JsonIgnore
    @ToString.Exclude
    private Lesson canceledLesson;

    @Valid
    @NotNull
    @Embedded
    private ScheduleDate newDate;

    //Нове місце(за потреби)
    private String newPlace;

    @Enumerated(EnumType.STRING)
    private PostponeStatus status = PostponeStatus.PENDING;

    public PostponeLesson(Lesson lesson, ScheduleDate newDate){
        this.canceledLesson = lesson;
        this.newDate = newDate;
    }

    public PostponeLesson(Lesson lesson, ScheduleDate newDate, String description){
        this.canceledLesson = lesson;
        this.newDate = newDate;
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        PostponeLesson that = (PostponeLesson) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Long.hashCode(id);
    }
}
