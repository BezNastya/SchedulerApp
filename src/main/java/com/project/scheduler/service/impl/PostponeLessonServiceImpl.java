package com.project.scheduler.service.impl;

import com.project.scheduler.entity.PostponeLesson;
import com.project.scheduler.entity.ScheduleDate;
import com.project.scheduler.entity.Teacher;
import com.project.scheduler.repository.PostponeLessonRepository;
import com.project.scheduler.service.PostponeLessonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostponeLessonServiceImpl implements PostponeLessonService {
    @Autowired
    PostponeLessonRepository postponeLessonRepository;

    @Override
    public PostponeLesson postponeLessonTimeAndDescription(long id, ScheduleDate newDate, String description) {
        PostponeLesson postponeLesson= postponeLessonRepository.getById(id);
        postponeLesson.setNewDate(newDate);
        postponeLesson.setDescription(description);
        postponeLessonRepository.save(postponeLesson);
        return postponeLesson;
    }

    @Override
    public PostponeLesson postponeLessonTimePlaceAndDescription(long id, ScheduleDate newDate, String description, String newPlace) {
        PostponeLesson postponeLesson= postponeLessonRepository.getById(id);
        postponeLesson.setNewDate(newDate);
        postponeLesson.setDescription(description);
        postponeLesson.setNewPlace(newPlace);
        postponeLessonRepository.save(postponeLesson);
        return postponeLesson;
    }

    @Override
    public List<PostponeLesson> getAllRequests() {
        return postponeLessonRepository.findAll();
    }

}
