package com.project.scheduler.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
public class GroupCourse {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn
    private Course course;

    private byte groupNum;

    @ManyToMany(mappedBy = "groupCourse")
    @JsonIgnore
    @ToString.Exclude
    Set<Student> students;

    @ManyToMany(mappedBy = "groupCourse")
    @JsonIgnore
    @ToString.Exclude
    Set<Teacher> teachers;

    public GroupCourse(byte groupNum){
        this.course = new Course();
        this.groupNum = groupNum;
        this.teachers = new HashSet<>();
        this.students = new HashSet<>();
    }

    public GroupCourse(Course course, byte groupNum){
        this.course = course;
        this.groupNum = groupNum;
        this.teachers = new HashSet<>();
        this.students = new HashSet<>();
    }

}
