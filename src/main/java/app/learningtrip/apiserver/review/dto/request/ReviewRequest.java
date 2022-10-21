package app.learningtrip.apiserver.review.dto.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ReviewRequest {

    @ApiModelProperty(value = "리뷰 아이디", example = "1")
    private Long id;

    @ApiModelProperty(value = "리뷰 관광지 아이디", example = "1")
    private Long place_id;

    @ApiModelProperty(value = "리뷰 내용", example = "좋았다!")
    private String content;

    @ApiModelProperty(value = "리뷰 별점", example = "1~5")
    private Integer rating;

    @ApiModelProperty(value = "리뷰 이미지1", example = "http://--")
    private String imageURL1;

    @ApiModelProperty(value = "리뷰 이미지2", example = "http://--")
    private String imageURL2;

    @ApiModelProperty(value = "리뷰 이미지3", example = "http://--")
    private String imageURL3;
}
