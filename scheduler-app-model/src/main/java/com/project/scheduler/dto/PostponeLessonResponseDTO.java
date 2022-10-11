package com.project.scheduler.dto;

import com.project.scheduler.entity.LessonOrder;
import com.project.scheduler.entity.WeekDay;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class PostponeLessonResponseDTO {
    private long lessonId;
    private int week;
    private WeekDay day;
    private LessonOrder order;
    private String description;
    private String courseName;
    private String oldDate;
    private String teachers;
    private String status;

    public PostponeLessonResponseDTO() {}
}
