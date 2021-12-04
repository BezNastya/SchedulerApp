package com.project.scheduler.entity;

public enum WeekDay {
    MONDAY("Monday"),
    TUESDAY("Tuesday"),
    WEDNESDAY("Wednesday"),
    THURSDAY("Thursday"),
    FRIDAY("Friday"),
    SATURDAY("Saturday");

    private final String day;

    WeekDay(final String day) {
        this.day = day;
    }

    public String getDay() {
        return day;
    }
}
