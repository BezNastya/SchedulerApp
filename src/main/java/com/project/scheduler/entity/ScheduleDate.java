package com.project.scheduler.entity;


import lombok.AllArgsConstructor;
import lombok.ToString;

import javax.persistence.Embeddable;

@Embeddable
public class ScheduleDate {

    int dayOfTheWeek;
    int lessonOrder;
    int week;

    public ScheduleDate(int day, int lesson, int week) {
        this.dayOfTheWeek = day;
        this.lessonOrder = lesson;
        this.week = week;
    }

    public ScheduleDate() {
    }

    @Override
    public String toString() {
        return String.format("Week %d Day %s Lesson %d", week, WeekDay.fromId(dayOfTheWeek).toString(), lessonOrder);
    }
}

