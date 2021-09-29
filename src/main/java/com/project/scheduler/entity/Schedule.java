package com.project.scheduler.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Data
@NoArgsConstructor
public class Schedule {
    private Long id;
    private List<Lesson> lessons;

    @Autowired
    public void setLessons(List<Lesson> lessons){
        this.lessons = lessons;
    }
}
