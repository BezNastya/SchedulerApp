package com.project.scheduler.entity;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Embeddable;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Embeddable
@Setter @Getter
public class ScheduleDate {

    @Min(0)
    @Max(6)
    @JsonProperty("dayOfTheWeek")
    int dayOfTheWeek;

    @Min(0)
    @JsonProperty("lessonOrder")
    int lessonOrder;

    @Min(0)
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

