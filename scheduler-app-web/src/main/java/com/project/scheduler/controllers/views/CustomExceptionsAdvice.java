package com.project.scheduler.controllers.views;

import com.project.scheduler.exceptions.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

@RestControllerAdvice
public class CustomExceptionsAdvice {

    Logger logger = LoggerFactory.getLogger(StudentController.class);

    @ExceptionHandler({NoTeachersOnCourseException.class, UserNotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String courseNotFoundHandler(Exception e){
        logger.warn(e.getMessage());
        return e.getMessage();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public String handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        logger.warn(ex.getMessage());
        return ex.getMessage();
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String defaultExceptionHandler(Exception e){
        logger.warn(e.getMessage());
        return (String.format("Internal server error occurred due to: %s", e.getMessage()));
    }

}
