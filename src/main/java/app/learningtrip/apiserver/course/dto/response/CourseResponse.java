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

    List<CoursePlaceResponse> placeList;
}
