package app.learningtrip.apiserver.course.dto.response;

import io.swagger.annotations.ApiModelProperty;
import java.util.List;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter @Setter @Builder
public class CourseResponse {

    @ApiModelProperty(example = "코스 아이디: Long")
    private Long id;

    @ApiModelProperty(example = "코스 이름: String")
    private String name;

    @ApiModelProperty(example = "코스 안에 포함된 관광지 리스트: List")
    List<CoursePlaceResponse> placeList;
}
