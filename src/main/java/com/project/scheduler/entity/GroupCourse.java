package com.project.scheduler.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
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
    @JoinColumn(insertable = false, updatable = false)
    private Course course;

    private byte groupNum;

    @ManyToMany(mappedBy = "groupCourse")
    @ToString.Exclude
    Set<Student> students;

    @ManyToMany(mappedBy = "groupCourse")
    @ToString.Exclude
    Set<Teacher> teachers;

    public GroupCourse(byte groupNum){
        this.groupNum = groupNum;
    }

}
