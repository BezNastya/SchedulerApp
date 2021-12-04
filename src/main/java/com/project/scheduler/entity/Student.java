package com.project.scheduler.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Set;


@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@PrimaryKeyJoinColumn(name = "userId")
public class Student extends EducationUser {

    public Student(final String firstName, final String lastName, final String faculty, final String specialty,
                   final String email, final String password) {
        super(email, password, firstName, lastName, "STUDENT");
        this.faculty = faculty;
        this.specialty = specialty;
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
