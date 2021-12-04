package com.project.scheduler.dto;

import com.project.scheduler.entity.Lesson;
import com.project.scheduler.entity.PostponeLesson;
import com.project.scheduler.entity.ScheduleDate;
import lombok.Data;

@Data
public class PostponeLessonDto {

    private String lesson;
    private String lessonType;
    private String description;
    private String teacher;
    private ScheduleDate oldDate;
    private ScheduleDate newDate;
    private String status;
    private Long id;

    public PostponeLessonDto(PostponeLesson postponeLesson){
        Lesson canceled = postponeLesson.getCanceledLesson();
        this.lesson = canceled.getGroupCourse().getCourse().getName();
        this.lessonType = canceled.getType().toString();
        this.description = postponeLesson.getDescription();
        this.teacher = canceled.getGroupCourse().getTeachers().toString();
        this.oldDate = canceled.getDate();
        this.newDate = postponeLesson.getNewDate();
        this.status = postponeLesson.getStatus().toString();
        this.id = postponeLesson.getId();
    }
}
