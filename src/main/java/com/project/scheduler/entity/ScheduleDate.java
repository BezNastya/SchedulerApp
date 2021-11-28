package com.project.scheduler.entity;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Embeddable;

@Embeddable
@Setter @Getter
public class ScheduleDate {

    @JsonProperty("dayOfTheWeek")
    int dayOfTheWeek;
    @JsonProperty("lessonOrder")
    int lessonOrder;
    @JsonProperty("week")
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

