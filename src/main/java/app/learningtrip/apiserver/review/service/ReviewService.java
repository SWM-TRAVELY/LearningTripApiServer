package app.learningtrip.apiserver.review.service;

import app.learningtrip.apiserver.place.domain.Place;
import app.learningtrip.apiserver.place.repository.PlaceRepository;
import app.learningtrip.apiserver.review.domain.Review;
import app.learningtrip.apiserver.review.dto.request.ReviewRequest;
import app.learningtrip.apiserver.review.dto.response.PlaceReviewResponse;
import app.learningtrip.apiserver.review.dto.response.UserReviewResponse;
import app.learningtrip.apiserver.review.repository.ReviewRepository;
import app.learningtrip.apiserver.user.domain.User;
import app.learningtrip.apiserver.user.repository.UserRepository;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final PlaceRepository placeRepository;
    private final UserRepository userRepository;

    private final HelpfulService helpfulService;

    /**
     * 리뷰 등록
     */
    public Long insert(ReviewRequest reviewRequest, User user) {
        // review 중복 조회
        reviewRepository.findByPlaceIdAndUserId(reviewRequest.getPlace_id(), user.getId())
            .ifPresent(m -> {
                throw new IllegalStateException("이미 등록한 리뷰가 있습니다.");
            });

        // place 조회
        Optional<Place> place = placeRepository.findById(reviewRequest.getPlace_id());
        place.orElseThrow(() -> new NoSuchElementException("존재하지 않은 Place입니다."));

        // 현재 일시 구하기
        Date date = new Date();
        System.out.println(date);

        // review 저장
        Review review = reviewRepository.save(Review.builder()
            .content(reviewRequest.getContent())
            .date(date)
            .rating(reviewRequest.getRating())
            .imageURL1(MakeNotNull(reviewRequest.getImageURL1()))
            .imageURL2(MakeNotNull(reviewRequest.getImageURL2()))
            .imageURL3(MakeNotNull(reviewRequest.getImageURL3()))
            .place(place.get())
            .user(user)
            .build());

        return review.getId();
    }

    /**
     * 리뷰 수정
     */
    public void modify(ReviewRequest reviewRequest) {

        // review 조회
        Optional<Review> review = reviewRepository.findById(reviewRequest.getId());
        review.orElseThrow(() -> new NoSuchElementException("존재하지 않은 review입니다."));

        // review 수정
        if (review.get().getContent() != null && review.get().getContent().equals(reviewRequest.getContent()) == false) {     // 내용 수정 시
            review.get().setContent(reviewRequest.getContent());
        }
        if (review.get().getRating() != null && review.get().getRating() != reviewRequest.getRating()) {                     // 별점 수정 시
            review.get().setRating(reviewRequest.getRating());
        }
        if (review.get().getImageURL1() != null && review.get().getImageURL1().equals(reviewRequest.getImageURL1()) == false) {    // imageURL1 수정 시
            review.get().setImageURL1(MakeNotNull(reviewRequest.getImageURL1()));
        }
        if (review.get().getImageURL2() != null && review.get().getImageURL2().equals(reviewRequest.getImageURL2()) == false) {    // imageURL2 수정 시
            review.get().setImageURL2(MakeNotNull(reviewRequest.getImageURL2()));
        }
        if (review.get().getImageURL3() != null && review.get().getImageURL3().equals(reviewRequest.getImageURL3()) == false) {    // imageURL3 수정 시
            review.get().setImageURL3(MakeNotNull(reviewRequest.getImageURL3()));
        }
        reviewRepository.save(review.get());
    }

    /**
     * 리뷰 삭제
     */
    public void delete(ReviewRequest reviewRequest) {

        // review 조회
        reviewRepository.deleteById(reviewRequest.getId());
    }

    /**
     * User별 리뷰 리스트 조회
     */
    public List<UserReviewResponse> reviewByUser(User user) {
        List<Review> reviewList = reviewRepository.findAllByUserId(user.getId());

        List<UserReviewResponse> userReviewResponses = new ArrayList<UserReviewResponse>();
        for (Review review : reviewList) {
            // place 조회
            Optional<Place> place = placeRepository.findById(review.getPlace().getId());
            place.orElseThrow(() -> new NoSuchElementException("존재하지 않은 Place의 Review입니다."));

            userReviewResponses.add(new UserReviewResponse(review, place.get()));
        }
        System.out.println(userReviewResponses);

        return userReviewResponses;
    }

    /**
     * Place별 리뷰 리스트 조회
     */
    public List<PlaceReviewResponse> reviewByPlace(Long place_id) {
        List<Review> reviewList = reviewRepository.findAllByPlaceId(place_id);

        List<PlaceReviewResponse> placeReviewResponses = new ArrayList<PlaceReviewResponse>();
        for (Review review : reviewList) {
            // user 조회
            Optional<User> user = userRepository.findById(review.getUser().getId());
            user.orElseThrow(() -> new NoSuchElementException("존재하지 않은 User의 Review입니다."));
            // user가 탈퇴했을 경우 리뷰 삭제 or 임의의 유저로 표시할 것인지 정책 결정필요

            // helpfulCount 찾기
            int helpfulCount = helpfulService.helpfulCountByReview(review.getId());

            // Response 생성
            placeReviewResponses.add(new PlaceReviewResponse(review, user.get(), helpfulCount));
        }

        System.out.println(placeReviewResponses);

        return placeReviewResponses;
    }

    /**
     * imageURL null값 없도록
     */
    private String MakeNotNull(String imageURL) {
        if (imageURL == null) {
            return "";
        }
        return imageURL;
    }
}
