package com.project.scheduler.entity;

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
public class Teacher extends User {

    @NotNull
    private String academicDegree;

    @NotNull
    private String department;


    @ManyToMany
    @JoinTable(
            name = "groupCourse_teacher",
            joinColumns = @JoinColumn(name = "userId"),
            inverseJoinColumns = @JoinColumn(name = "groupCourse_id"))
    @ToString.Exclude
    Set<GroupCourse> groupCourse;


}
