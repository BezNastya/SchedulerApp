package com.project.scheduler.entity;

import lombok.Data;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.util.List;
import java.util.Map;

@Data
@Entity
@Table(name = "course")
public class Course {

    @Column(name = "id")
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "name")
    String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id", nullable = true)
    Student student;


    //TODO problem1 Map in DB
//    @ElementCollection
//    Map<Integer, List<Lesson>> groupToLessons;

}
