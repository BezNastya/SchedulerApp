package com.project.scheduler.service;

import com.project.scheduler.entity.PostponeLesson;
import com.project.scheduler.entity.ScheduleDate;
import com.project.scheduler.entity.Teacher;
//import javafx.geometry.Pos;

import java.util.List;

public interface PostponeLessonService {

    PostponeLesson postponeLesson(long id, PostponeLesson postponeLesson);
    List<PostponeLesson> getAllRequests();
}
