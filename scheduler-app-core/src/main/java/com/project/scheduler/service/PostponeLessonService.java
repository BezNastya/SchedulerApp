package com.project.scheduler.service;

import com.project.scheduler.entity.PostponeLesson;

import java.util.List;

public interface PostponeLessonService {

    PostponeLesson postponeLesson(long id, PostponeLesson postponeLesson);
    List<PostponeLesson> getAllRequests();
    boolean approveRequest(long id);
    boolean declineRequest(long id);
    void deleteRequest(long id);
}
