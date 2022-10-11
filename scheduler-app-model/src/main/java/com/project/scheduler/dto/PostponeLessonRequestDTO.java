package com.project.scheduler.dto;

import com.project.scheduler.entity.LessonOrder;
import com.project.scheduler.entity.WeekDay;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PostponeLessonRequestDTO {
    private long lessonId;
    private int week;
    private WeekDay day;
    private LessonOrder order;
    private String description;
    private String teachers;
}
