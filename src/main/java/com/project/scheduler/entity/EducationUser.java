package com.project.scheduler.entity;


import com.fasterxml.jackson.annotation.JsonBackReference;
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
@PrimaryKeyJoinColumn(name = "userId")
public class EducationUser extends User {


    public EducationUser(final String firstName, final String lastName, final String email,
                         final String password, final String role) {
        super(email, password, firstName, lastName, role);
    }

    @JsonBackReference
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "GROUP_USER",
            joinColumns = @JoinColumn(name = "userId"),
            inverseJoinColumns = @JoinColumn(name = "groupCourse_id"))
    @ToString.Exclude
    Set<GroupCourse> groupCourse;

}
