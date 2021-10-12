package com.project.scheduler.entity;

import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.*;
import java.util.Objects;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
public class Teacher{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(unique = true)
    String email;

    String password;

    @NotNull
    private String firstName;

    @NotNull
    private String lastName;

    @NotNull
    private String academicDegree;

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
        return Long.hashCode(id);
    }
}
