package com.project.scheduler.service.impl;

import com.project.scheduler.entity.Lesson;
import com.project.scheduler.entity.PostponeLesson;
import com.project.scheduler.repository.LessonRepository;
import com.project.scheduler.repository.PostponeLessonRepository;
import com.project.scheduler.service.PostponeLessonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PostponeLessonServiceImpl implements PostponeLessonService {

    @Autowired
    LessonRepository lessonRepository;

    @Autowired
    PostponeLessonRepository postponeLessonRepository;

    @Override
    public PostponeLesson postponeLesson(long id, PostponeLesson postponeLesson) {
        Lesson lessonToPostpone= lessonRepository.getById(id);
        postponeLesson.setCanceledLesson(lessonToPostpone);
        postponeLessonRepository.save(postponeLesson);
        return postponeLesson;
    }

    @Override
    public List<PostponeLesson> getAllRequests() {
        return postponeLessonRepository.findAll();
    }

}
