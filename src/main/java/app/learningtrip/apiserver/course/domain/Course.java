package app.learningtrip.apiserver.course.domain;

import app.learningtrip.apiserver.place.dto.response.PlaceResponse;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Builder
public class Course {

    private long id;

    private String name;

    private List<PlaceResponse> placeList;
}
