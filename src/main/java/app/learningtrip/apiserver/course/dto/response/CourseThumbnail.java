package app.learningtrip.apiserver.course.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CourseThumbnail {

    private long id;

    private String name;

    private String imageURL;

    private String place_1;

    private String place_2;

    private String place_3;
}
