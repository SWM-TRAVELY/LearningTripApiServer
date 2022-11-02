package app.learningtrip.apiserver.course.controller;

import app.learningtrip.apiserver.configuration.auth.PrincipalDetails;
import app.learningtrip.apiserver.course.dto.request.CourseRequest;
import app.learningtrip.apiserver.course.dto.response.CourseResponse;
import app.learningtrip.apiserver.course.dto.response.CourseThumbnail;
import app.learningtrip.apiserver.course.service.CourseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import java.io.IOException;
import java.rmi.NoSuchObjectException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.json.JSONException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

@Api(tags = {"코스 API"})
@RestController
@RequiredArgsConstructor
public class CourseController {

    private final CourseService courseService;

    @GetMapping("/course/make")
    @ApiIgnore
    public ResponseEntity setMakeCourse() {
        courseService.makeCourses();
        return ResponseEntity.ok().body(200);
    }

    @GetMapping("/home/course/recommend")
    @ApiOperation(value = "추천 코스 조회", notes = "사용자에게 추천하는 코스를 조회한다.")
    public ResponseEntity<List<CourseThumbnail>> getRecommendCourse() throws NoSuchObjectException {
        List<CourseThumbnail> courseThumbnailList = courseService.getRecommend();

        return ResponseEntity.ok().body(courseThumbnailList);
    }

    @GetMapping("/course/list")
    @ApiOperation(value = "코스목록 조회", notes = "사용자별 코스목록을 조회한다.")
    public ResponseEntity<List<CourseThumbnail>> courseList(@AuthenticationPrincipal PrincipalDetails user) {
        List<CourseThumbnail> courseThumbnailList = courseService.getList(user.getUser());

        return ResponseEntity.ok().body(courseThumbnailList);
    }

    @GetMapping("/course/{course_id}")
    @ApiOperation(value = "코스정보 조회", notes = "코스 정보를 조회한다.")
    @ApiImplicitParam(name = "course_id", value = "코스 아이디")
    public ResponseEntity getCourse(@PathVariable(name = "course_id") long course_id)
        throws JSONException, IOException {
        CourseResponse courseResponse = courseService.getInfo(course_id);

        return ResponseEntity.ok().body(courseResponse);
    }

    @PostMapping("/course")
    @ApiOperation(value = "코스 생성", notes = "코스를 생성한다.")
    public ResponseEntity setCourse(@RequestBody CourseRequest courseRequest, @AuthenticationPrincipal PrincipalDetails user) {
        System.out.println(courseRequest);
        courseService.setCourse(courseRequest, user.getUser());

        return ResponseEntity.ok().body(200);
    }

    @PutMapping("/course")
    @ApiOperation(value = "코스 수정", notes = "코스를 수정한다.")
    public ResponseEntity modifyCourse(@RequestBody CourseRequest courseRequest, @AuthenticationPrincipal PrincipalDetails user) {
        courseService.setCourse(courseRequest, user.getUser());

        return ResponseEntity.ok().body(200);
    }
    @DeleteMapping("/course")
    @ApiOperation(value = "코스 삭제", notes = "코스를 삭제한다.")
    public ResponseEntity deleteCourse(@RequestBody CourseRequest courseRequest, @AuthenticationPrincipal PrincipalDetails user) {
        courseService.delete(courseRequest, user.getUser());

        return ResponseEntity.ok().body(200);
    }

    @GetMapping("/distance")
    public ResponseEntity getGoogleDistance() throws JSONException, IOException {

        return ResponseEntity.ok().body(courseService.getGoogleMapApi(3.0, 127.0, 3.0, 127.0));
    }

}
