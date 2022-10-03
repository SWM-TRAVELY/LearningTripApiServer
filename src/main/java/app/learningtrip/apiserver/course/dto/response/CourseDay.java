package app.learningtrip.apiserver.course.dto.response;

import app.learningtrip.apiserver.place.domain.Place;
import java.util.ArrayList;
import java.util.List;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter @Setter @Builder
public class CourseDay {

    int day;

    List<CoursePlaceResponse> placeList;

    public static CourseDay toResponse(int day, List<Place> placeList) {

        // placeList 생성
        List<CoursePlaceResponse> coursePlaceList = new ArrayList<CoursePlaceResponse>();
        for (Place place : placeList) {
            coursePlaceList.add(CoursePlaceResponse.toResponse(place));
        }

        // courseDay 반환
        return CourseDay.builder()
            .day(day)
            .placeList(coursePlaceList)
            .build();

    }
}
