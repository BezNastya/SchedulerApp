package com.project.scheduler.entity;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class Course {

    int id;
    String name;
    Map<Integer, List<Lesson>> groupToLessons;

}
