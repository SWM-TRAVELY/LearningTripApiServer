package app.learningtrip.apiserver.review.dto.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class HelpfulRequest {

    @ApiModelProperty(value = "리뷰 아이디", example = "1")
    private Long review_id;

    @ApiModelProperty(value = "도움 여부", example = "0 or 1")
    private boolean helpful;
}
