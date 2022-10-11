package com.project.scheduler.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CourseDTO {

    private int id;
    private String name;
    private byte numberOfGroups;

    public CourseDTO(String name, byte numberOfGroups) {
        this.name = name;
        this.numberOfGroups = numberOfGroups;
    }
}
