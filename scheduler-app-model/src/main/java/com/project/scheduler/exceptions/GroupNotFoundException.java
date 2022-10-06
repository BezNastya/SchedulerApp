package com.project.scheduler.exceptions;

import com.project.scheduler.entity.Course;

public class GroupNotFoundException extends RuntimeException{

    public GroupNotFoundException(Course course, byte num){
        super(String.format("Group with num %s not found for course %s", num, course));
    }
}
