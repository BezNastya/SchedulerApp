package com.project.scheduler.entity;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Set;


@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@PrimaryKeyJoinColumn(name = "userId")
public class Student extends User {

    @NotNull
    private String studTicketSeries;

    @NotNull
    private String faculty;

    @NotNull
    private String specialty;

    @NotNull
    private int yearAdmission;


    @ManyToMany
    @JoinTable(
            name = "groupCourse_student",
            joinColumns = @JoinColumn(name = "userId"),
            inverseJoinColumns = @JoinColumn(name = "groupCourse_id"))
    @ToString.Exclude
    Set<GroupCourse> groupCourse;

}
