package com.project.scheduler.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;

import javax.persistence.*;

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
    private String description;

    //Предмет, що перенесено
    @OneToOne
    @JoinColumn(name = "canceledLesson", nullable = false)
    @JsonIgnore
    @ToString.Exclude
    private Lesson canceledLesson;

    @Embedded
    private ScheduleDate newDate;

    //Нове місце(за потреби)
    private String newPlace;

    public PostponeLesson(Lesson lesson, ScheduleDate newDate){
        this.canceledLesson = lesson;
        this.newDate = newDate;
        this.description = "Description for postponing";
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
