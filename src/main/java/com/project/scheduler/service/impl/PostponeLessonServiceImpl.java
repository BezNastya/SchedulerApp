package com.project.scheduler.service.impl;

import com.project.scheduler.entity.Lesson;
import com.project.scheduler.entity.PostponeLesson;
import com.project.scheduler.entity.PostponeStatus;
import com.project.scheduler.repository.LessonRepository;
import com.project.scheduler.repository.PostponeLessonRepository;
import com.project.scheduler.service.PostponeLessonService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Service
public class PostponeLessonServiceImpl implements PostponeLessonService {

    @Autowired
    LessonRepository lessonRepository;

    @Autowired
    PostponeLessonRepository postponeLessonRepository;

    @Override
    public PostponeLesson postponeLesson(final long id, @Valid final PostponeLesson postponeLesson) {
        Lesson lessonToPostpone = lessonRepository.getById(id);
        postponeLesson.setCanceledLesson(lessonToPostpone);
        postponeLessonRepository.save(postponeLesson);
        return postponeLesson;
    }

    @Override
    public List<PostponeLesson> getAllRequests() {
        return postponeLessonRepository.findAll();
    }

    @Override
    public boolean approveRequest(long id) {
        Optional<PostponeLesson> request = postponeLessonRepository.findById(id);
        if (request.isPresent()) {
            PostponeLesson postpone = request.get();
            postpone.setStatus(PostponeStatus.APPROVED);
            postponeLessonRepository.save(postpone);
            Lesson canceled = postpone.getCanceledLesson();
            canceled.setDate(postpone.getNewDate());
            if (StringUtils.isNotBlank(postpone.getNewPlace())) {
                canceled.setPlace(postpone.getNewPlace());
            }
            lessonRepository.save(canceled);
            return true;
        }
        return false;
    }

    @Override
    public boolean declineRequest(long id) {
        Optional<PostponeLesson> request = postponeLessonRepository.findById(id);
        if (request.isPresent()) {
            PostponeLesson postpone = request.get();
            postpone.setStatus(PostponeStatus.DECLINED);
            postponeLessonRepository.save(postpone);
            return true;
        }
        return false;
    }

    @Override
    public void deleteRequest(long id) {
        postponeLessonRepository.deleteById(id);
    }


}
