package com.project.scheduler.entity;

import lombok.Data;

import java.util.List;
import java.util.Objects;

@Data
public class Teacher {
    private long id;
    private String firstName;
    private String lastName;
    private String academicDegree;
    private String department;
    private List<Course> courses;

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
