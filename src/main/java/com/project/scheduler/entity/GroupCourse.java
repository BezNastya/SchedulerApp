package com.project.scheduler.entity;

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
public class GroupCourse {
    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(insertable = false, updatable = false)
    private Course course_id;


    private byte group_num;

    @ManyToMany(mappedBy = "groupCourse")
    @ToString.Exclude
    Set<User> likes;

}
