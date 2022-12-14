package app.learningtrip.apiserver.course.controller;

import app.learningtrip.apiserver.configuration.auth.PrincipalDetails;
import app.learningtrip.apiserver.course.dto.request.CourseCreate;
import app.learningtrip.apiserver.course.dto.request.CourseRequest;
import app.learningtrip.apiserver.category.dto.CourseOptionsResponse;
import app.learningtrip.apiserver.course.dto.response.CourseResponse;
import app.learningtrip.apiserver.course.dto.response.CourseThumbnail;
import app.learningtrip.apiserver.course.service.CourseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import java.io.IOException;
import java.rmi.NoSuchObjectException;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import lombok.RequiredArgsConstructor;
import org.json.JSONException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = {"코스 API"})
@RestController
@RequiredArgsConstructor
public class CourseController {

    private final CourseService courseService;

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
    @ApiOperation(value = "추천 코스정보 조회", notes = "추천 코스의 정보를 조회한다.")
    @ApiImplicitParam(name = "course_id", value = "코스 아이디")
    public ResponseEntity getCourse(@PathVariable(name = "course_id") long course_id)
        throws JSONException, IOException {
        CourseResponse courseResponse = courseService.getRecommendCourseInfo(course_id);

        return ResponseEntity.ok().body(courseResponse);
    }

    @GetMapping("/course/user/{course_id}")
    @ApiOperation(value = "유저 코스정보 조회", notes = "유저의 코스 정보를 조회한다.")
    @ApiImplicitParam(name = "course_id", value = "코스 아이디")
    public ResponseEntity getUserCourse(@PathVariable(name = "course_id") long course_id, @AuthenticationPrincipal PrincipalDetails user)
        throws JSONException, IOException {
        CourseResponse courseResponse = courseService.getUserCourseInfo(course_id, user.getUser());

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

    @PostMapping("/course/recommend")
    @ApiOperation(value = "코스 생성 추천", notes = "사용자가 입력한 값에 따라 코스를 생성하여 추천다.")
    public ResponseEntity createGetCourse(@RequestBody CourseCreate courseCreate)
        throws NoSuchObjectException {
        System.out.println(courseCreate.getStart());
        List<CourseThumbnail> courseThumbnailList = courseService.getRecommend();

        return ResponseEntity.ok().body(courseThumbnailList);
    }
}
