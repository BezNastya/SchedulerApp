package com.project.scheduler.entity;
import com.sun.istack.NotNull;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.util.List;
import java.util.Map;
import java.util.Objects;


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

    @Column(name = "studTicketNumber", unique = true)
    @NotNull
    private String studTicketNumber;

    @Column(name = "faculty")
    @NotNull
    private String faculty;

    @Column(name = "course")
    @NotNull
    private String course;

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

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
