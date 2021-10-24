package com.project.scheduler.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.Min;
import java.util.Set;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
public class GroupCourse {
    @Id
    @Column(nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(insertable = false, updatable = false)
    private Course course_id;

    @Min(value = 1,message = "Group numbers must be positive")
    private byte group_num;

    @ManyToMany(mappedBy = "groupCourse")
    @ToString.Exclude
    Set<Student> students;

    @ManyToMany(mappedBy = "groupCourse")
    @ToString.Exclude
    Set<Teacher> teachers;

}
