package app.learningtrip.apiserver.course.controller;

import app.learningtrip.apiserver.course.dto.response.CourseThumbnailListResponse;
import app.learningtrip.apiserver.course.dto.response.CourseThumbnail;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CourseController {

    @GetMapping("/course/list")
    public ResponseEntity<CourseThumbnailListResponse> courseList() {
        List<CourseThumbnail> courseThumbnailList = new ArrayList<CourseThumbnail>();
        courseThumbnailList.add(CourseThumbnail.builder()
            .id(1)
            .name("시은이의 신나는 경주여행 코스!~1")
            .imageURL("https://www.google.com/imgres?imgurl=https%3A%2F%2Fwww.gyeongju.go.kr%2Fupload%2Fcontent%2Fthumb%2F20191221%2F61FBB0185F5E46ADBEC117779F2F8150.jpg&imgrefurl=https%3A%2F%2Fwww.gyeongju.go.kr%2Ftour%2Fpage.do%3Fmnu_uid%3D2695%26con_uid%3D246%26cmd%3D2&tbnid=BAwAYSr4z2Lg9M&vet=12ahUKEwjo7t-ihZv6AhVK_JQKHdJJAK0QMygCegUIARDgAQ..i&docid=aX6WYCeZiJBRFM&w=1600&h=1067&q=%EB%B6%88%EA%B5%AD%EC%82%AC&ved=2ahUKEwjo7t-ihZv6AhVK_JQKHdJJAK0QMygCegUIARDgAQ")
            .place_1("불국사")
            .place_2("첨성대")
            .place_3("석가탑")
            .build());
        courseThumbnailList.add(CourseThumbnail.builder()
            .id(2)
            .name("시은이의 신나는 경주여행 코스!~2")
            .imageURL("https://www.google.com/imgres?imgurl=https%3A%2F%2Fwww.gyeongju.go.kr%2Fupload%2Fcontent%2Fthumb%2F20191221%2F61FBB0185F5E46ADBEC117779F2F8150.jpg&imgrefurl=https%3A%2F%2Fwww.gyeongju.go.kr%2Ftour%2Fpage.do%3Fmnu_uid%3D2695%26con_uid%3D246%26cmd%3D2&tbnid=BAwAYSr4z2Lg9M&vet=12ahUKEwjo7t-ihZv6AhVK_JQKHdJJAK0QMygCegUIARDgAQ..i&docid=aX6WYCeZiJBRFM&w=1600&h=1067&q=%EB%B6%88%EA%B5%AD%EC%82%AC&ved=2ahUKEwjo7t-ihZv6AhVK_JQKHdJJAK0QMygCegUIARDgAQ")
            .place_1("불국사")
            .place_2("첨성대")
            .place_3(null)
            .build());
        courseThumbnailList.add(CourseThumbnail.builder()
            .id(3)
            .name("시은이의 신나는 경주여행 코스!~3")
            .imageURL(null)
            .place_1(null)
            .place_2(null)
            .place_3(null)
            .build());

        CourseThumbnailListResponse courseThumbnailListResponse = new CourseThumbnailListResponse(courseThumbnailList);

        return ResponseEntity.ok().body(courseThumbnailListResponse);
    }

}
