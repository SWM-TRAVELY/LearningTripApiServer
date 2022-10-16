package app.learningtrip.apiserver.review.dto.response;

import app.learningtrip.apiserver.review.domain.Review;
import app.learningtrip.apiserver.user.domain.User;
import java.text.SimpleDateFormat;
import java.util.Date;
import lombok.Data;

@Data
public class PlaceReviewResponse {

    private Long id;

    private String content;

    private Integer rating;

    private String imageURL1;

    private String imageURL2;

    private String imageURL3;

    private String date;

    private String nickname;

    private String profileImage;

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
