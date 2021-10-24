package com.project.scheduler.controllers;

import com.project.scheduler.exceptions.CourseNotFoundException;
import com.project.scheduler.exceptions.NoTeachersOnCourseException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestControllerAdvice
public class CustomExceptionsAdvice {

    @ExceptionHandler(CourseNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String productNotFoundHandler(CourseNotFoundException e){
        return e.getMessage();
    }

    @ExceptionHandler(NoTeachersOnCourseException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String noTeachersHandler(NoTeachersOnCourseException e){
        return e.getMessage();
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String defaultExceptionHandler(Exception e){
        return (String.format("Internal server error occurred due to: %s", e.getMessage()));
    }
}
