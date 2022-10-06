package com.project.scheduler.entity;

import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;


@Getter
@Setter
@ToString
@Entity
@PrimaryKeyJoinColumn(name = "userId")
public class Student extends EducationUser {

    public Student(String firstName, String lastName, String faculty, String specialty,
                   String email, String password) {
        super(email, password, firstName, lastName, Role.STUDENT);
        this.faculty = faculty;
        this.specialty = specialty;
    }

    public Student() {
    }

    @NotNull
    private String studTicketSeries;

    @NotNull
    private String faculty;

    @NotNull
    private String specialty;

    @NotNull
    private int yearAdmission;

}
