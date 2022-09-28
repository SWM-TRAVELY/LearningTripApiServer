package app.learningtrip.apiserver.course.dto.response;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter @Setter @Builder
public class CoursePlaceResponse {

    Long id;

    String name;

    String description;

    String imageURL;

    String address;
}
