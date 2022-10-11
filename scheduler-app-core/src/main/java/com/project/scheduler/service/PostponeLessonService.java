package com.project.scheduler.service;
import com.project.scheduler.Constansts;
import com.project.scheduler.dto.PostponeLessonRequestDTO;
import com.project.scheduler.dto.PostponeLessonResponseDTO;
import com.project.scheduler.entity.GroupCourse;
import com.project.scheduler.entity.Role;
import com.project.scheduler.entity.Teacher;
import com.project.scheduler.entity.User;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostponeLessonService {


    public void postponeLesson(PostponeLessonRequestDTO postponeLesson) {
        RestTemplate restTemplate = new RestTemplate();
        String saveUrl = Constansts.courseServiceUrl + "/requests";
        HttpEntity<PostponeLessonRequestDTO> httpEntity = new HttpEntity<>(postponeLesson);
        ResponseEntity<Void> response = restTemplate.exchange(saveUrl, HttpMethod.POST, httpEntity, Void.class);
        if (!response.getStatusCode().is2xxSuccessful()) {
            throw new RuntimeException("Internal server error: Can not call course-service");
        }
    }

    public boolean approveRequest(long lessonId) {
        RestTemplate restTemplate = new RestTemplate();
        String saveUrl = Constansts.courseServiceUrl + "/requests/approve?lessonId=" + lessonId;
        ResponseEntity<Boolean> response = restTemplate.exchange(saveUrl, HttpMethod.PUT, null, Boolean.class);
        if (!response.getStatusCode().is2xxSuccessful()) {
            throw new RuntimeException("Internal server error: Can not call course-service");
        }
        return Boolean.TRUE.equals(response.getBody());
    }


    public boolean declineRequest(long id) {
        RestTemplate restTemplate = new RestTemplate();
        String saveUrl = Constansts.courseServiceUrl + "/requests/decline?lessonId=" + id;
        ResponseEntity<Boolean> response = restTemplate.exchange(saveUrl, HttpMethod.PUT, null, Boolean.class);
        if (!response.getStatusCode().is2xxSuccessful()) {
            throw new RuntimeException("Internal server error: Can not call course-service");
        }
        return Boolean.TRUE.equals(response.getBody());
    }

    public void deleteRequest(long id) {
        RestTemplate restTemplate = new RestTemplate();
        String url = Constansts.courseServiceUrl + "/requests/" + id;
        restTemplate.delete(url);
    }

    public PostponeLessonResponseDTO getRequestByLessonId(long id) {
        RestTemplate restTemplate = new RestTemplate();
        String url = Constansts.courseServiceUrl + "/requests/byLesson?lessonId=" + id;
        ResponseEntity<PostponeLessonResponseDTO> response = restTemplate.exchange(url, HttpMethod.GET, null, PostponeLessonResponseDTO.class);
        if (!response.getStatusCode().is2xxSuccessful()) {
            throw new RuntimeException("Internal server error: Can not call course-service");
        }
        return response.getBody();
    }

    public List<PostponeLessonResponseDTO> getAllRequestsForUser(User user) {
        if (user.getRole().equals(Role.ADMIN)) {
            return getAllRequests();
        }
        if (!user.getRole().equals(Role.TEACHER)) {
            return Collections.emptyList();
        }
        List<Long> groupCourses = ((Teacher) user).getGroupCourse().stream()
                .map(GroupCourse::getId)
                .collect(Collectors.toList());
        RestTemplate restTemplate = new RestTemplate();
        String url = Constansts.courseServiceUrl + "/requests";
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url);
        builder.queryParam("groupCourseIds", groupCourses.stream()
                .map(x -> Long.toString(x))
                .collect(Collectors.joining(",")));
        URI uri = builder.build().encode().toUri();
        ResponseEntity<List<PostponeLessonResponseDTO>> response = restTemplate.exchange(uri, HttpMethod.GET, null, new ParameterizedTypeReference<>(){});
        if (!response.getStatusCode().is2xxSuccessful()) {
            throw new RuntimeException("Internal server error: Can not call course-service");
        }
        return response.getBody();
    }

    private List<PostponeLessonResponseDTO> getAllRequests() {
        RestTemplate restTemplate = new RestTemplate();
        String url = Constansts.courseServiceUrl + "/requests";
        ResponseEntity<List<PostponeLessonResponseDTO>> response = restTemplate.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<>() {
        });
        if (!response.getStatusCode().is2xxSuccessful()) {
            throw new RuntimeException("Internal server error: Can not call course-service");
        }
        return response.getBody();
    }
}
