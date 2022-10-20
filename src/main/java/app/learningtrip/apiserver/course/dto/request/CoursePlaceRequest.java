package app.learningtrip.apiserver.course.dto.request;

import lombok.Data;

@Data
public class CoursePlaceRequest {

    private Long id;

    private Integer day;

    private Integer sequence;
}
