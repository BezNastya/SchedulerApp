package com.project.scheduler.exceptions;

import com.project.scheduler.entity.Course;

public class LessonNotFoundException extends RuntimeException{

    public LessonNotFoundException(Long id){
        super(String.format("Lesson with id %d not found ", id));
    }
}
