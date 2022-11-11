package app.learningtrip.apiserver.course.service;

import app.learningtrip.apiserver.course.domain.CoursePlaceRecommend;
import app.learningtrip.apiserver.course.domain.CourseRecommend;
import app.learningtrip.apiserver.course.domain.CourseUser;
import app.learningtrip.apiserver.course.domain.CoursePlaceUser;
import app.learningtrip.apiserver.course.domain.GoogleMapApi;
import app.learningtrip.apiserver.course.dto.request.CoursePlaceRequest;
import app.learningtrip.apiserver.course.dto.request.CourseRequest;
import app.learningtrip.apiserver.course.dto.response.CoursePlaceResponse;
import app.learningtrip.apiserver.course.dto.response.CourseResponse;
import app.learningtrip.apiserver.course.dto.response.CourseThumbnail;
import app.learningtrip.apiserver.course.repository.CoursePlaceRecommendRepository;
import app.learningtrip.apiserver.course.repository.CoursePlaceUserRepository;
import app.learningtrip.apiserver.course.repository.CourseRecommendRepository;
import app.learningtrip.apiserver.course.repository.CourseUserRepository;
import app.learningtrip.apiserver.place.domain.Place;
import app.learningtrip.apiserver.place.repository.PlaceRepository;
import app.learningtrip.apiserver.place.service.PlaceService;
import app.learningtrip.apiserver.user.domain.User;
import app.learningtrip.apiserver.user.repository.UserRepository;
import java.rmi.NoSuchObjectException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import okhttp3.ResponseBody;
import org.json.JSONException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.io.IOException;

@Service
@Transactional
@RequiredArgsConstructor
public class CourseService {

    private final CourseUserRepository courseUserRepository;
    private final CoursePlaceUserRepository coursePlaceUserRepository;
    private final CourseRecommendRepository courseRecommendRepository;
    private final CoursePlaceRecommendRepository coursePlaceRecommendRepository;
    private final PlaceService placeService;
    private final PlaceRepository placeRepository;
    private final UserRepository userRepository;

    private final GoogleMapApi googleMapApi;

    /**
     * Google-Map-Api
     *
     * @return
     */
    public ResponseBody getGoogleMapApi(double latitude1, double longitude1, double latitude2, double longitude2) throws IOException, JSONException {
        googleMapApi.makeDistanceTime(latitude1,longitude1,latitude2,longitude2);
        System.out.println(googleMapApi.getDistance());
        System.out.println(googleMapApi.getTime());

        return null;
    }

    /**
     * 유저 코스 정보 조회
     */
    public CourseResponse getUserCourseInfo(Long id, User user) throws JSONException, IOException {

        // course table 조회
        Optional<CourseUser> course = courseUserRepository.findById(id);
        List<CoursePlaceUser> coursePlaceUserList = coursePlaceUserRepository.findAllByCourseIdOrderByDayAscSequenceAsc(id);

        if (course.isPresent() == false) {
            throw new RuntimeException(Long.toString(id) + "번의 코스 정보를 찾을 수 없습니다.");
        }

        if (!course.get().user.equals(user)) {
            throw new RuntimeException("코스 정보에 접근할 권한이 없습니다.");
        }

        List<CoursePlaceResponse> coursePlaceResponses = new ArrayList<CoursePlaceResponse>();
        Double latitude = null;
        Double longitude = null;
        for (CoursePlaceUser coursePlaceUser : coursePlaceUserList) {
            Integer distance = 0;
            Integer time = 0;
            if (latitude != null & longitude != null) {
                googleMapApi.makeDistanceTime(latitude, longitude, coursePlaceUser.place.getLatitude(), coursePlaceUser.place.getLongitude());
                distance = googleMapApi.getDistance();
                time = googleMapApi.getTime();
            }
            latitude = coursePlaceUser.place.getLatitude();
            longitude = coursePlaceUser.place.getLongitude();
            coursePlaceResponses.add(CoursePlaceResponse.toResponse(coursePlaceUser, distance, time));
        }

        CourseResponse courseResponse = CourseResponse.builder()
            .id(course.get().getId())
            .name(course.get().getName())
            .placeList(coursePlaceResponses)
            .build();

        return courseResponse;
    }

    /**
     * 추천 코스 정보 조회
     */
    public CourseResponse getRecommendCourseInfo(Long id) throws JSONException, IOException {

        // course table 조회
        Optional<CourseRecommend> course = courseRecommendRepository.findById(id);
        List<CoursePlaceRecommend> coursePlaceUserList = coursePlaceRecommendRepository.findAllByCourseIdOrderByDayAscSequenceAsc(id);

        if (course.isPresent() == false) {
            throw new RuntimeException(Long.toString(id) + "번의 코스 정보를 찾을 수 없습니다.");
        }

        List<CoursePlaceResponse> coursePlaceResponses = new ArrayList<CoursePlaceResponse>();
        Double latitude = null;
        Double longitude = null;
        for (CoursePlaceRecommend coursePlaceRecommend : coursePlaceUserList) {
            Integer distance = 0;
            Integer time = 0;
            if (latitude != null & longitude != null) {
                googleMapApi.makeDistanceTime(latitude, longitude, coursePlaceRecommend.place.getLatitude(), coursePlaceRecommend.place.getLongitude());
                distance = googleMapApi.getDistance();
                time = googleMapApi.getTime();
            }
            latitude = coursePlaceRecommend.place.getLatitude();
            longitude = coursePlaceRecommend.place.getLongitude();
            coursePlaceResponses.add(CoursePlaceResponse.toResponse(coursePlaceRecommend, distance, time));
        }

        CourseResponse courseResponse = CourseResponse.builder()
            .id(course.get().getId())
            .name(course.get().getName())
            .placeList(coursePlaceResponses)
            .build();

        return courseResponse;
    }

    /**
     * 코스 리스트 조회
     */
    public List<CourseThumbnail> getList(User user) {

        List<CourseThumbnail> courseThumbnailList = new ArrayList<CourseThumbnail>();

        // course table 조회
        List<CourseUser> courseUserList = courseUserRepository.findAllByUserId(user.getId());
        if (courseUserList.size() == 0) {
            return courseThumbnailList;
        }

        // course_place table 조회
        for (CourseUser courseUser : courseUserList) {
            List<CoursePlaceUser> coursePlaceUserList = coursePlaceUserRepository.findAllByCourseIdOrderByDayAscSequenceAsc(
                courseUser.getId());

            List<Place> placeList = new ArrayList<Place>();
            for (CoursePlaceUser coursePlaceUser : coursePlaceUserList) {
                placeList.add(coursePlaceUser.getPlace());
            }

            courseThumbnailList.add(CourseThumbnail.toThumbnail(courseUser, placeList));
        }

        return courseThumbnailList;
    }

    /**
     * 코스 생성
     */
    public void setCourse(CourseRequest courseRequest, User user) {
        System.out.println("in service");

        Optional<CourseUser> course = Optional.empty();
        if (courseRequest.getId() == null) {
            course = Optional.ofNullable(courseUserRepository.save(CourseUser.builder()
                .name(courseRequest.getName())
                .user(user)
                .build()));
        }
        else {
            // Course 탐색, 없으면 생성
            course = courseUserRepository.findById(courseRequest.getId());

            course.get().setName(courseRequest.getName());
            courseUserRepository.save(course.get());
        }

        // Course-Place에서 현재 course 데이터 삭제
        coursePlaceUserRepository.deleteAllByCourseId(course.get().getId());

        // Course-Place 생성
        List<CoursePlaceRequest> placeList = courseRequest.getPlaceList();
        for (CoursePlaceRequest coursePlaceRequest : placeList) {
            Optional<Place> place = placeRepository.findById(coursePlaceRequest.getId());
            place.orElseThrow(() -> new NoSuchElementException("존재하지 않은 Place입니다."));

            coursePlaceUserRepository.save(CoursePlaceUser.builder()
                .day(coursePlaceRequest.getDay())
                .sequence(coursePlaceRequest.getSequence())
                .course(course.get())
                .place(place.get())
                .build());
        }
    }

    /**
     * 코스 삭제
     */
    public void delete(CourseRequest courseRequest, User user) {
        // Course 탐색, 없으면 생성
        Optional<CourseUser> course = courseUserRepository.findById(courseRequest.getId());
        course.orElseThrow(() -> new NoSuchElementException("존재하지 않은 Place입니다."));

        // Course-Place에서 course 데이터 삭제
        coursePlaceUserRepository.deleteAllByCourseId(course.get().getId());

        // Course에서 삭제
        courseUserRepository.delete(course.get());
    }


    /**
     * 홈 추천 코스 표시
     */
    public List<CourseThumbnail> getRecommend() throws NoSuchObjectException {
        int countOfCourse = courseRecommendRepository.countAllBy();

        List<CourseThumbnail> courseThumbnailList = new ArrayList<CourseThumbnail>();

        List<Integer> duplicationCheckList = new ArrayList<Integer>();
        for (int i = 0; i < 4; i++) {
            int index = (int)(Math.random() * countOfCourse) + 1;

            // 중복 체크
            int flag = 0;
            for (int listLength = 0; listLength < duplicationCheckList.size(); listLength++) {
                if (index == duplicationCheckList.get(listLength)) {
                    flag = 1;
                    break;
                }
            }
            if (flag == 1) {
                i--;
                continue;
            }

            // 코스 탐색
            Optional<CourseRecommend> course = courseRecommendRepository.findById(Long.valueOf(index));
            course.orElseThrow(() -> new NoSuchObjectException("없는 경로를 조회했습니다."));

            List<CoursePlaceRecommend> coursePlaceRecommendList = coursePlaceRecommendRepository.findAllByCourseIdOrderByDayAscSequenceAsc(Long.valueOf(index));
            List<Place> placeList = new ArrayList<Place>();
            for (CoursePlaceRecommend coursePlaceRecommend : coursePlaceRecommendList) {
                placeList.add(coursePlaceRecommend.getPlace());
            }

            CourseThumbnail courseThumbnail = CourseThumbnail.toThumbnail(course.get(), placeList);
            courseThumbnailList.add(courseThumbnail);
        }
        return courseThumbnailList;
    }

    /**
     * 추천 코스 생성
     */
    public void makePlaces(String region, int days, CourseRecommend courseRecommend) {
        int placeCountOfDay = 2;

        List<Place> placeList =  placeService.findSeoulTextbookPlace();

        List<Integer> duplicationCheckList = new ArrayList<Integer>();
        for (int day = 1; day <= days; day++) {
            for (int placeCount = 0; placeCount < placeCountOfDay; placeCount++) {
                int index = (int)(Math.random() * placeList.size());

                // 중복 체크
                int flag = 0;
                for (int listLength = 0; listLength < duplicationCheckList.size(); listLength++) {
                    if (index == duplicationCheckList.get(listLength)) {
                        flag = 1;
                    }
                }
                if (flag == 1) {
                    placeCount--;
                    continue;
                }

                duplicationCheckList.add(index);

                coursePlaceRecommendRepository.save(CoursePlaceRecommend.builder()
                    .day(day)
                    .sequence(placeCount+1)
                    .course(courseRecommend)
                    .place(placeList.get(index))
                    .build());
            }
        }
    }

    public void makeCourse(String region, int days, int num) {
        String name;
        if (days == 1) {
            name = "당일치기 "+region+" 여행 "+Integer.toString(num+1);
        } else {
            name = Integer.toString(days-1)+"박 "+Integer.toString(days)+"일 "+region+" 여행 "+Integer.toString(num+1);
        }

        Optional<User> user = userRepository.findById(1L);

        CourseRecommend courseRecommend = courseRecommendRepository.save(CourseRecommend.builder()
            .name(name)
            .user(user.get())
            .build());

        makePlaces(region, days, courseRecommend);
    }

    public void makeCourses() {
        List<String> regions = new ArrayList<String>();
        regions.add("서울");
        //regions.add("경주");

        List<CourseThumbnail> courseThumbnailList = new ArrayList<CourseThumbnail>();
        for (String region : regions) {
            for (int days = 1; days < 5; days ++) {
                for (int num = 0; num < 30; num++) {
                    makeCourse(region, days, num);
                }
            }
        }
    }
}
