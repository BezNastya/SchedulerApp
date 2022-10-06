package com.project.scheduler.exceptions;

public class NoTeachersOnCourseException extends RuntimeException{

    public NoTeachersOnCourseException(){
        super("Course must have at least one teacher");
    }
}
