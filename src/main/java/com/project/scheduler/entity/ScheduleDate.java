package com.project.scheduler.entity;


import javax.persistence.Embeddable;

@Embeddable
public class ScheduleDate {

    int dayOfTheWeek;
    int lessonOrder;
    int week;

}
