package app.learningtrip.apiserver.course.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CourseThumbnail {

    private long id;

    private String name;

    private String imageURL;

    private String place1;

    private String place2;

    private String place3;
}
