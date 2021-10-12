package com.project.scheduler.entity;

import com.sun.istack.NotNull;
import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;


@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
public class Student extends User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    String email;
    String password;

    @NotNull
    private String firstName;

    @NotNull
    private String lastName;

    @NotNull
    private String secondName;

    @NotNull
    private int age;

    @NotNull
    private String studTicketSeries;

    @NotNull
    private String faculty;

    @NotNull
    private String specialty;

    @NotNull
    private int yearAdmission;


         //@OneToMany(fetch = FetchType.LAZY, mappedBy = "student")
         //private List<Course> course;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Student student = (Student) o;
        return id == student.id;
    }

    @Override
    public int hashCode() {
        return Long.hashCode(id);
    }
}
