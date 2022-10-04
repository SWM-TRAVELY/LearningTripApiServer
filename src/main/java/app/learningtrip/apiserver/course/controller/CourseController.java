package app.learningtrip.apiserver.course.controller;

import app.learningtrip.apiserver.course.domain.CoursePlace;
import app.learningtrip.apiserver.course.dto.response.CourseDay;
import app.learningtrip.apiserver.course.dto.response.CoursePlaceResponse;
import app.learningtrip.apiserver.course.dto.response.CourseResponse;
import app.learningtrip.apiserver.course.dto.response.CourseThumbnail;
import app.learningtrip.apiserver.course.service.CourseService;
import java.rmi.NoSuchObjectException;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CourseController {

    private final CourseService courseService;

    @GetMapping("/course/make")
    public ResponseEntity setMakeCourse() {
        courseService.makeCourses();
        return ResponseEntity.ok().body(200);
    }

    @GetMapping("/home/course/recommend")
    public ResponseEntity<List<CourseThumbnail>> getRecommendCourse() throws NoSuchObjectException {
        List<CourseThumbnail> courseThumbnailList = courseService.getRecommend();

        return ResponseEntity.ok().body(courseThumbnailList);
    }

    @GetMapping("/course/list")
    public ResponseEntity<List<CourseThumbnail>> courseList() {
        List<CourseThumbnail> courseThumbnailList = courseService.getList();

        return ResponseEntity.ok().body(courseThumbnailList);
    }

    @GetMapping("/course/{course_id}")
    public ResponseEntity getCourse(@PathVariable(name = "course_id") long course_id, Model model) {
        Model model_result = courseService.getInfoNotDay(course_id, model);

        return ResponseEntity.ok().body(model_result);
    }

}
