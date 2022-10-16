package app.learningtrip.apiserver.review.controller;

import app.learningtrip.apiserver.configuration.auth.PrincipalDetails;
import app.learningtrip.apiserver.review.dto.request.ReviewRequest;
import app.learningtrip.apiserver.review.dto.response.PlaceReviewResponse;
import app.learningtrip.apiserver.review.dto.response.UserReviewResponse;
import app.learningtrip.apiserver.review.service.ReviewService;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping("/review")
    public Long insertReview(@RequestBody ReviewRequest reviewRequest, @AuthenticationPrincipal PrincipalDetails user) {

        Long review_id = reviewService.insert(reviewRequest, user.getUser());

        return review_id;
    }

    @PatchMapping("/review")
    public ResponseEntity modifyReview(@RequestBody ReviewRequest reviewRequest) {

        reviewService.modify(reviewRequest);

        return ResponseEntity.ok().body(200);
    }

    @DeleteMapping("/review")
    public ResponseEntity deleteReview(@RequestBody ReviewRequest reviewRequest) {

        reviewService.delete(reviewRequest);

        return ResponseEntity.ok().body(200);
    }

    @GetMapping("/review")
    public ResponseEntity getPlaceReview(@AuthenticationPrincipal PrincipalDetails user) {
        List<UserReviewResponse> reviewList = reviewService.reviewByUser(user.getUser());

        return ResponseEntity.ok().body(reviewList);
    }

    @GetMapping("/review/{place_id}")
    public ResponseEntity getPlaceReview(@PathVariable(name = "place_id") long place_id) {
        List<PlaceReviewResponse> reviewList = reviewService.reviewByPlace(place_id);

        return ResponseEntity.ok().body(reviewList);
    }
}
