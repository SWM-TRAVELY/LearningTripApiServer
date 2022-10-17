package app.learningtrip.apiserver.review.dto.request;

import lombok.Data;

@Data
public class ReviewRequest {

    private Long id;

    private Long place_id;

    private String content;

    private Integer rating;

    private String imageURL1;

    private String imageURL2;

    private String imageURL3;
}
