package app.learningtrip.apiserver.course.dto.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class CoursePlaceRequest {

    @ApiModelProperty(value = "관광지 아이디", example = "1")
    private Long id;

    @ApiModelProperty(value = "관광지를 방문할 일차", example = "1")
    private Integer day;

    @ApiModelProperty(value = "해당 일차의 관광지 순서", example = "1")
    private Integer sequence;
}
