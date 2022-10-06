package com.project.scheduler.entity;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
@RequiredArgsConstructor
@Entity
@PrimaryKeyJoinColumn(name = "userId")
@NoArgsConstructor
public class Teacher extends EducationUser {

    public Teacher(final String firstName, final String lastName, final String department, final String academicDegree,
                   final String email, final String password) {
        super(firstName, lastName, email, password, Role.TEACHER);
        this.department = department;
        this.academicDegree = academicDegree;
    }

    @NotNull
    @NotBlank(message = "Every teacher must have an academic degree")
    private String academicDegree;

    @NotNull
    @NotBlank(message = "Every user must have a department!")
    private String department;

    @Override
    public String toString() {
        return firstName + " " + lastName;
    }
}
