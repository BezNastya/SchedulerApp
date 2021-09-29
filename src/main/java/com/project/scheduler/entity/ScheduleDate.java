package com.project.scheduler.entity;

import org.springframework.stereotype.Component;

@Component
public class ScheduleDate {

    int dayOfTheWeek;
    int lessonOrder;

    // If weekStart = 3 and weekEnd = 6, then the lesson will be conducted on 3-6 weeks
    int weekStart; // Specifies the start week for this lesson
    int weekEnd; // Specifies the end week (inclusive) for this lesson

}
