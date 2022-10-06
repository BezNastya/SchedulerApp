package com.project.scheduler.entity;

public enum LessonType {
    LECTURE("Lecture"),
    SEMINAR("Seminar"),
    PRACTICE("Practice"),
    LAB("Lab");

    private final String type;

    LessonType(final String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

}
