package com.project.scheduler.entity;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Objects;

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
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "canceledLesson", nullable = false)
    @ToString.Exclude
    private Lesson canceledLesson;

    @Embedded
    private ScheduleDate newDate;

    //Нове місце(за потреби)
    private String newPlace;

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
