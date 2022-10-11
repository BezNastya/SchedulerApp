package com.project.scheduler.dto;

import com.project.scheduler.entity.LessonOrder;
import com.project.scheduler.entity.LessonType;
import com.project.scheduler.entity.WeekDay;
import lombok.*;

import java.util.Objects;

@Getter
@Setter
@ToString
@Builder
public class LessonDTO {

    private long lessonId;
    private LessonType type;
    private String place;
    private int week;
    private LessonOrder time;
    private WeekDay day;
    private String courseName;
    private String teachers;
    private byte groupNum;
    private long groupId;
    private Long postponeLesson;
    private String postponeStatus;

    public LessonDTO(long lessonId, LessonType type, String place, int week, LessonOrder time, WeekDay day,
                     String courseName, String teachers, byte groupNum, long groupId, long postponeLesson,
                     String postponeStatus) {
        this.lessonId = lessonId;
        this.type = type;
        this.place = place;
        this.week = week;
        this.time = time;
        this.day = day;
        this.courseName = courseName;
        this.teachers = teachers;
        this.groupNum = groupNum;
        this.groupId = groupId;
        this.postponeLesson = postponeLesson;
        this.postponeStatus = postponeStatus;
    }

    public LessonDTO() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LessonDTO lesson = (LessonDTO) o;
        return type == lesson.type && Objects.equals(place, lesson.place) && Objects.equals(time, lesson.time)
                && Objects.equals(courseName, lesson.courseName) && Objects.equals(groupNum, lesson.groupNum);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, place, time, courseName, groupNum);
    }
}
