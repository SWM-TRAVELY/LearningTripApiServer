package app.learningtrip.apiserver.course.dto.response;

import app.learningtrip.apiserver.course.domain.Course;
import app.learningtrip.apiserver.course.domain.CoursePlace;
import app.learningtrip.apiserver.place.domain.Place;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter @Setter @Builder
public class CourseResponse {

    private long id;

    private String name;

    private List<CourseDay> dayList;

    public static CourseResponse toResponse(Optional<Course> course, List<CoursePlace> coursePlace) {

        // CoursePlace DB에서 가져온 데이터 Map<day, List<Place>> 형태로 변경
        Map<Integer, List<Place>> days = new HashMap<Integer, List<Place>>();
        for (CoursePlace oneCoursePlace : coursePlace) {
            int key = oneCoursePlace.getDay();
            if (days.containsKey(key)) {
                days.get(key).add(oneCoursePlace.getPlace());
            } else {
                List<Place> placeList = new ArrayList<Place>();
                placeList.add(oneCoursePlace.getPlace());
                days.put(key, placeList);
            }
        }

        // dayList 생성
        List<CourseDay> courseDays = new ArrayList<CourseDay>();
        for (Integer day : days.keySet()) {
            courseDays.add(CourseDay.toResponse(day, days.get(day)));
        }

        return CourseResponse.builder()
            .id(course.get().getId())
            .name(course.get().getName())
            .dayList(courseDays)
            .build();
    }
}
