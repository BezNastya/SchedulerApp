package com.project.scheduler.exceptions;

public class CourseNotFoundException extends RuntimeException{

    public CourseNotFoundException(Long id){
        super(String.format("Course with id %s not found", id));
    }
}
