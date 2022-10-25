package app.learningtrip.apiserver.review.dto.response;

import app.learningtrip.apiserver.place.domain.Place;
import app.learningtrip.apiserver.review.domain.Review;
import io.swagger.annotations.ApiModelProperty;
import java.text.SimpleDateFormat;
import java.util.Date;
import lombok.Data;


@Data
public class UserReviewResponse {

    @ApiModelProperty(value = "리뷰 아이디", example = "1")
    private Long id;

    @ApiModelProperty(value = "리뷰 내용", example = "좋았다!")
    private String content;

    @ApiModelProperty(value = "리뷰 별점", example = "1~5")
    private Integer rating;

    @ApiModelProperty(value = "이미지 개수", example = "3")
    private Integer imageCount;

    @ApiModelProperty(value = "리뷰 작성일자", example = "2022-10-21")
    private String date;

    @ApiModelProperty(value = "방문한 관광지 대표 이미지", example = "http://--")
    private String placeImageURL;

    @ApiModelProperty(value = "방문한 관광지 이름", example = "경복궁")
    private String placeName;

    public UserReviewResponse(Review review, Place place) {
        this.id = review.getId();
        this.content = review.getContent();
        this.rating = review.getRating();
        this.imageCount = review.getImageCount();

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = dateFormat.format(review.getDate());
        this.date = dateString;

        this.placeImageURL = place.getImageURL1();
        this.placeName = place.getName();
    }
}
