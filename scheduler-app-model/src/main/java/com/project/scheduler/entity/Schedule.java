package com.project.scheduler.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class Schedule {
    private Long id;
    private List<Lesson> lessons;

}
