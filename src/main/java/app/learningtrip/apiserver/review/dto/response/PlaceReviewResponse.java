package app.learningtrip.apiserver.review.dto.response;

import app.learningtrip.apiserver.review.domain.Review;
import app.learningtrip.apiserver.user.domain.User;
import io.swagger.annotations.ApiModelProperty;
import java.text.SimpleDateFormat;
import java.util.Date;
import lombok.Data;

@Data
public class PlaceReviewResponse {

    @ApiModelProperty(value = "리뷰 이미지2", example = "http://--")
    private Long id;

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

    @ApiModelProperty(value = "리뷰 작성일자", example = "2022-10-21")
    private String date;

    @ApiModelProperty(value = "리뷰 작성 유저 닉네임", example = "sieun")
    private String nickname;

    @ApiModelProperty(value = "리뷰 작성 유저 프로필 사진", example = "http://--")
    private String profileImage;

    @ApiModelProperty(value = "'도움이 되었습니다' 개수", example = "0~")
    private Integer helpfulCount;

    public PlaceReviewResponse(Review review, User user, int helpfulCount) {
        this.id = review.getId();
        this.content = review.getContent();
        this.rating = review.getRating();
        this.imageURL1 = review.getImageURL1();
        this.imageURL2 = review.getImageURL2();
        this.imageURL3 = review.getImageURL3();

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = dateFormat.format(review.getDate());
        this.date = dateString;

        if (user.getNickname() == null) {
            this.nickname = "회원";
        }
        else {
            this.nickname = user.getNickname();
        }
        this.profileImage = user.getImage();

        this.helpfulCount = helpfulCount;
    }
}
