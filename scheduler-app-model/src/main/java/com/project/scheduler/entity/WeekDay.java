package com.project.scheduler.entity;

import java.util.Arrays;

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

    @Override
    public String toString() {
        return day;
    }

    public static WeekDay fromString(String day) {
        return Arrays.stream(WeekDay.values()).filter(x -> x.getDay().equals(day))
                .findFirst().orElse(null);
    }
}
