package com.project.scheduler.entity;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Objects;


@Data
public class Student extends User {

    private long id;

    private String firstName;

    private String lastName;
    private String secondName;

    private int age;

    private String studTicketSeries;
    private int studTicketNumber;

    private String faculty;

    private String specialty;
    private int yearAdmission;



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return id == student.getId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
