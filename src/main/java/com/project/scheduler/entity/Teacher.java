package com.project.scheduler.entity;

import com.sun.istack.NotNull;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Data
@Entity
@Table(name = "teacher")
public class Teacher{

    @Column(name = "id")
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "email" , unique = true)
    String email;

    @Column(name = "password")
    String password;



    @Column(name = "firstName")
    @NotNull
    private String firstName;

    @Column(name = "lastName")
    @NotNull
    private String lastName;

    @Column(name = "academicDegree")
    @NotNull
    private String academicDegree;

    @Column(name = "department")
    @NotNull
    private String department;

    //another variant, with cascade
    //@OneToMany(fetch = FetchType.LAZY, mappedBy = "teacher", cascade = CascadeType.ALL)
//    @OneToMany(fetch = FetchType.LAZY, mappedBy = "teacher")
//    private Set<Lesson> lessons = new HashSet<>();
//    private List<Course> courses;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Teacher teacher = (Teacher) o;
        return id == teacher.getId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
