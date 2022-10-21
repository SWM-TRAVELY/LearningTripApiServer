package app.learningtrip.apiserver.course.dto.request;

import io.swagger.annotations.ApiModelProperty;
import java.util.List;
import lombok.Data;

@Data
public class CourseRequest {

    @ApiModelProperty(value = "코스 아이디", example = "1")
    private Long id;

    @ApiModelProperty(value = "코스 이름", example = "2박 3일 서울 여행")
    private String name;

    @ApiModelProperty(value = "코스에 포함된 관광지 목록")
    private List<CoursePlaceRequest> placeList;
}
