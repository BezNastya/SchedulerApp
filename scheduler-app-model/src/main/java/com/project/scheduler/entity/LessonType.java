package com.project.scheduler.entity;

import java.util.Arrays;

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

    @Override
    public String toString() {
        return type;
    }

    public static LessonType fromString(String type) {
        return Arrays.stream(LessonType.values()).filter(x -> x.getType().equals(type))
                .findFirst().orElse(null);
    }

}
