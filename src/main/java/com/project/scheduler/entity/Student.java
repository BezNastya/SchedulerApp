package com.project.scheduler.entity;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;



@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@PrimaryKeyJoinColumn(name = "userId")
public class Student extends User{
/*
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
*/

    @NotNull
    private String studTicketSeries;

    @NotNull
    private String faculty;

    @NotNull
    private String specialty;

    @NotNull
    private int yearAdmission;


         //@OneToMany(fetch = FetchType.LAZY, mappedBy = "student")
         //private List<Course> course;
/*
    @MapsId
    @OneToOne(mappedBy = "student")
    @JoinColumn(name = "id")   //same name as id @Column
    private User user;
*/

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Student student = (Student) o;
        return userId == student.userId;
    }

    @Override
    public int hashCode() {
        return Long.hashCode(userId);
    }
}
