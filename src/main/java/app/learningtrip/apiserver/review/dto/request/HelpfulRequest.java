package app.learningtrip.apiserver.review.dto.request;

import lombok.Data;

@Data
public class HelpfulRequest {

    private Long review_id;

    private boolean helpful;
}
