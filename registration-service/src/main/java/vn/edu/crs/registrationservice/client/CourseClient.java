package vn.edu.crs.registrationservice.client;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
public class CourseClient {

    private final RestTemplate restTemplate;

    public Object getCourseById(Long courseId) {
        String url = "http://localhost:8082/courses/" + courseId;
        return restTemplate.getForObject(url, Object.class);
    }

    public Object reserveSeat(Long courseId) {
        String url = "http://localhost:8082/courses/" + courseId + "/reserve-seat";
        return restTemplate.exchange(url, HttpMethod.PATCH, HttpEntity.EMPTY, Object.class).getBody();
    }

    public Object releaseSeat(Long courseId) {
        String url = "http://localhost:8082/courses/" + courseId + "/release-seat";
        return restTemplate.exchange(url, HttpMethod.PATCH, HttpEntity.EMPTY, Object.class).getBody();
    }
}