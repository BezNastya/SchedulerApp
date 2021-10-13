package com.project.scheduler.entity;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Set;

@ToString
@RequiredArgsConstructor
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    protected long userId;

    @NotNull
    @Setter
    @Getter
    String email;

    @NotNull
    @Setter
    @Getter
    String password;

    @NotNull
    @Setter
    @Getter
    private String firstName;

    @NotNull
    @Setter
    @Getter
    private String lastName;
/*
    @NotNull
    private String role;
*/
    @NotNull
    private boolean authorized;

    @ManyToMany
    @JoinTable(
            name = "groupCourse_like",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "groupCourse_id"))
    @ToString.Exclude
    Set<GroupCourse> groupCourse;

/*
    @OneToOne(cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private Student student;

    @OneToOne(cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private Teacher teacher;

    @OneToOne(cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private Admin admin;
*/

}
