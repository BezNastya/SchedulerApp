package com.project.scheduler.entity;
import com.sun.istack.NotNull;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.util.*;


@Data
@Entity
@Table(name = "student")
public class Student extends User {

    @Column(name = "id")
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;



    @Column(name = "email")
    String email;

    @Column(name = "password")
    String password;

    @Column(name = "firstName")
    @NotNull
    private String firstName;

    @Column(name = "lastName")
    @NotNull
    private String lastName;

    @Column(name = "secondName")
    @NotNull
    private String secondName;

    @Column(name = "age")
    @NotNull
    private int age;

    @Column(name = "studTicketSeries", unique = true)
    @NotNull
    private String studTicketSeries;

    @Column(name = "faculty")
    @NotNull
    private String faculty;



    @Column(name = "specialty")
    @NotNull
    private String specialty;

    @Column(name = "yearAdmission")
    @NotNull
    private int yearAdmission;



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return id == student.getId();
    }



    @OneToMany(fetch = FetchType.LAZY, mappedBy = "student")
    private Course course;

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
