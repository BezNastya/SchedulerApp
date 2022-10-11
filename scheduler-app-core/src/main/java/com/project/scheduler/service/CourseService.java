package com.project.scheduler.service;

import com.project.scheduler.Constansts;
import com.project.scheduler.dto.CourseDTO;
import com.project.scheduler.dto.GroupDTO;
import com.project.scheduler.dto.LessonDTO;
import com.project.scheduler.dto.LessonRequestDTO;
import com.project.scheduler.entity.GroupCourse;
import com.project.scheduler.entity.WeekDay;
import com.project.scheduler.repository.GroupCourseRepository;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.springframework.web.util.UriComponentsBuilder.fromHttpUrl;

@Service
@Slf4j
@Transactional
public class CourseService {

    private final Logger logger = LoggerFactory.getLogger(CourseService.class);

    private final GroupCourseRepository groupRepository;

    @Autowired
    public CourseService(GroupCourseRepository groupCourseRepository) {
        this.groupRepository = groupCourseRepository;
    }


    public void saveCourse(CourseDTO course) {
        RestTemplate restTemplate = new RestTemplate();
        String saveUrl = Constansts.courseServiceUrl + "/courses";
        HttpEntity<CourseDTO> courseDTOHttpEntity = new HttpEntity<>(course);
        ResponseEntity<List<Long>> response = restTemplate.exchange(saveUrl, HttpMethod.POST, courseDTOHttpEntity, new ParameterizedTypeReference<>() {
        });
        if (!response.getStatusCode().is2xxSuccessful()) {
            throw new RuntimeException("Internal server error: Can not call course-service");
        }
        for (byte i = 1; i <= course.getNumberOfGroups(); i++) {
            GroupCourse groupCourse = new GroupCourse();
            groupCourse.setGroupNum(i);
            groupCourse.setId(response.getBody().get(i - 1));
            groupRepository.save(groupCourse);
        }
    }

    public void deleteCourseById(Long courseId) {
        RestTemplate restTemplate = new RestTemplate();
        String url = Constansts.courseServiceUrl + "/courses/" + courseId;
        restTemplate.delete(url);
    }

    public List<CourseDTO> findAll() {
        RestTemplate restTemplate = new RestTemplate();
        String url = Constansts.courseServiceUrl + "/courses";
        ResponseEntity<List<CourseDTO>> response = restTemplate.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<>() {
        });
        if (!response.getStatusCode().is2xxSuccessful()) {
            throw new RuntimeException("Internal server error: Can not call course-service");
        }
        return response.getBody();
    }


    public List<GroupDTO> findAllGroupsForCourse(long course) {
        RestTemplate restTemplate = new RestTemplate();
        String url = Constansts.courseServiceUrl + "/groups?courseId=" + course;
        ResponseEntity<List<GroupDTO>> response = restTemplate.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<>(){});
        if (!response.getStatusCode().is2xxSuccessful()) {
            throw new RuntimeException("Internal server error: Can not call course-service");
        }
        return response.getBody();
    }


    public GroupCourse findGroupById(Long id) {
        return groupRepository.findById(id).orElse(null);
    }


    public List<GroupCourse> findGroupCoursesByEducationUserId(Long id) {
        logger.warn("Getting all groups for the user with id " + id);
        return groupRepository.findGroupCoursesByEducationUserId(id);
    }

    public List<LessonDTO> findAllLessonsByWeek(int week) {
        RestTemplate restTemplate = new RestTemplate();
        String url = Constansts.courseServiceUrl + "/lessons/byWeek?week=" + week;
        ResponseEntity<List<LessonDTO>> response = restTemplate.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<>(){});
        if (!response.getStatusCode().is2xxSuccessful()) {
            throw new RuntimeException("Internal server error: Can not call course-service");
        }
        return response.getBody();
    }

    public List<CourseDTO> findNotAttendedCourses(Long id) {
        List<GroupCourse> groupCourses = findGroupCoursesByEducationUserId(id);
        List<Long> res = groupCourses.stream()
                .map(GroupCourse::getId)
                .collect(Collectors.toList());
        return findAllCoursesForGroups(res);
    }

    private List<GroupCourse> findAllGroups() {
        return groupRepository.findAll();
    }

    private List<CourseDTO> findAllCoursesForGroups(List<Long> groupCourses) {
        RestTemplate restTemplate = new RestTemplate();
        String url = Constansts.courseServiceUrl + "/courses/notGroups";
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url);
        builder.queryParam("groupIds", groupCourses.stream()
                .map(x -> Long.toString(x))
                .collect(Collectors.joining(",")));
        URI uri = builder.build().encode().toUri();
        ResponseEntity<List<CourseDTO>> response = restTemplate.exchange(uri, HttpMethod.GET, null, new ParameterizedTypeReference<>(){});
        if (!response.getStatusCode().is2xxSuccessful()) {
            throw new RuntimeException("Internal server error: Can not call course-service");
        }
        return response.getBody();
    }

    public List<LessonDTO> findLessonsByEducationUserId(long id) {
        List<Long> groupCourseList =
                groupRepository.findGroupCoursesByEducationUserId(id)
                        .stream().map(GroupCourse::getId)
                        .collect(Collectors.toList());
        RestTemplate restTemplate = new RestTemplate();
        String url = Constansts.courseServiceUrl + "/lessons";
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url);
        builder.queryParam("groupCourseIds", groupCourseList.stream()
                .map(x -> Long.toString(x))
                .collect(Collectors.joining(",")));
        URI uri = builder.build().encode().toUri();
        ResponseEntity<List<LessonDTO>> response = restTemplate.exchange(uri, HttpMethod.GET, null, new ParameterizedTypeReference<>(){});
        if (!response.getStatusCode().is2xxSuccessful()) {
            throw new RuntimeException("Internal server error: Can not call course-service");
        }
        return response.getBody();
    }

    public Map<WeekDay, List<LessonDTO>> findScheduleForWeek(int week, long id) {
        List<LessonDTO> lessonsList = findLessonsByEducationUserId(id).stream().filter(l -> l.getWeek() == week).collect(Collectors.toList());
        Map<WeekDay, List<LessonDTO>> result = new TreeMap<>();
        Stream.of(WeekDay.values()).forEach((weekDay -> result.put(weekDay, lessonsList.stream().filter(l -> l.getDay().equals(weekDay))
                .collect(Collectors.toList()))));
        return result;
    }

    public void addLessons(LessonRequestDTO requestDTO) {
        RestTemplate restTemplate = new RestTemplate();
        String saveUrl = Constansts.courseServiceUrl + "/lessons";
        HttpEntity<LessonRequestDTO> httpEntity = new HttpEntity<>(requestDTO);
        ResponseEntity<Void> response = restTemplate.exchange(saveUrl, HttpMethod.POST, httpEntity, Void.class);
        if (!response.getStatusCode().is2xxSuccessful()) {
            throw new RuntimeException("Internal server error: Can not call course-service");
        }
    }

    public LessonDTO findLessonById(Long id) {
        RestTemplate restTemplate = new RestTemplate();
        String url = Constansts.courseServiceUrl + "/lessons/" + id;
        ResponseEntity<LessonDTO> response = restTemplate.exchange(url, HttpMethod.GET, null, LessonDTO.class);
        if (!response.getStatusCode().is2xxSuccessful()) {
            throw new RuntimeException("Internal server error: Can not call course-service");
        }
        return response.getBody();
    }

    public void editLessons(LessonRequestDTO requestDTO) {
        RestTemplate restTemplate = new RestTemplate();
        String saveUrl = Constansts.courseServiceUrl + "/lessons";
        HttpEntity<LessonRequestDTO> httpEntity = new HttpEntity<>(requestDTO);
        ResponseEntity<Void> response = restTemplate.exchange(saveUrl, HttpMethod.PUT, httpEntity, Void.class);
        if (!response.getStatusCode().is2xxSuccessful()) {
            throw new RuntimeException("Internal server error: Can not call course-service");
        }
    }

    public void deleteLessonById(Long id) {
        RestTemplate restTemplate = new RestTemplate();
        String url = Constansts.courseServiceUrl + "/lessons/" + id;
        restTemplate.delete(url);
    }

    public List<GroupDTO> findAllGroupsFotGroupsIds(List<Long> groupCourseIds) {
        RestTemplate restTemplate = new RestTemplate();
        String url = Constansts.courseServiceUrl + "/groups/byIds";
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url);
        builder.queryParam("ids", groupCourseIds.stream()
                .map(x -> Long.toString(x))
                .collect(Collectors.joining(",")));
        URI uri = builder.build().encode().toUri();
        ResponseEntity<List<GroupDTO>> response = restTemplate.exchange(uri, HttpMethod.GET, null, new ParameterizedTypeReference<>(){});
        if (!response.getStatusCode().is2xxSuccessful()) {
            throw new RuntimeException("Internal server error: Can not call course-service");
        }
        return response.getBody();
    }
}
