package app.learningtrip.apiserver.review.dto.response;

import app.learningtrip.apiserver.place.domain.Place;
import app.learningtrip.apiserver.review.domain.Review;
import java.text.SimpleDateFormat;
import java.util.Date;
import lombok.Data;

@Data
public class UserReviewResponse {

    private Long id;

    private String content;

    private Integer rating;

    private String imageURL1;

    private String imageURL2;

    private String imageURL3;

    private String date;

    private String placeImageURL;

    private String placeName;

    public UserReviewResponse(Review review, Place place) {
        this.id = review.getId();
        this.content = review.getContent();
        this.rating = review.getRating();
        this.imageURL1 = review.getImageURL1();
        this.imageURL2 = review.getImageURL2();
        this.imageURL3 = review.getImageURL3();

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = dateFormat.format(review.getDate());
        this.date = dateString;

        this.placeImageURL = place.getImageURL1();
        this.placeName = place.getName();
    }
}
