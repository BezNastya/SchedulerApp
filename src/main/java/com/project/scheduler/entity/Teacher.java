package com.project.scheduler.entity;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@PrimaryKeyJoinColumn(name = "userId")
public class Teacher extends User{

    /*
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
*/

    @NotNull
    private String academicDegree;

    @NotNull
    private String department;
/*
    @MapsId
    @OneToOne(mappedBy = "teacher")
    @JoinColumn(name = "id")   //same name as id @Column
    private User user;
*/
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
        return userId == teacher.userId;
    }

    @Override
    public int hashCode() {
        return Long.hashCode(userId);
    }
}
