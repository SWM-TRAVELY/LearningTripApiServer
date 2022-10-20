package app.learningtrip.apiserver.course.dto.request;

import java.util.List;
import lombok.Data;

@Data
public class CourseRequest {

    private long id;

    private String name;

    private List<CoursePlaceRequest> placeList;
}
