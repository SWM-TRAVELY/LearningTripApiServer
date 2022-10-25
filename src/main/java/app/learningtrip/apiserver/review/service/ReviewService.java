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
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final PlaceRepository placeRepository;
    private final UserRepository userRepository;

    private final HelpfulService helpfulService;

    private final AmazonS3Client amazonS3Client;

    @Value("${aws.s3.bucket-name}")
    private String s3BucketName;

    /**
     * 리뷰 등록
     */
    public Long insert(List<MultipartFile> images, ReviewRequest reviewRequest, User user)
        throws IOException {
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
            .imageCount(images.isEmpty() ? 0 : images.size()) // images가 빈 리스트거나 null이면 0
            .place(place.get())
            .user(user)
            .build());

//      이미지 1장 이상일때, S3에 업로드
        if(review.getImageCount() != 0){
            uploadImages(images, review.getId());
        }

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
        // Todo: 이미지 수정 로직
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

    /**
     * AWS S3 버킷에 이미지 업로드
     * @param images MultipartFile 이미지 리스트
     * @param reviewId 리뷰ID
     * @return 업로드 이미지 링크 리스트
     * @throws IOException getInputStream 에서 예외 발생 가능
     */
    private List<String> uploadImages(List<MultipartFile> images, Long reviewId)
        throws IOException {

        List<String> imageList = new ArrayList<>();
        String path = "https://image.learningtrip.app/";

        for(int i=0; i<images.size(); i++) {
            String fileName = "review/" + Long.toString(reviewId) + "_" + Integer.toString(i) + ".jpg";

            MultipartFile image = images.get(i);

            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentType(image.getContentType());
            objectMetadata.setContentLength(image.getSize());

            amazonS3Client.putObject(
                new PutObjectRequest(s3BucketName, fileName, image.getInputStream(), objectMetadata)
                    .withCannedAcl(CannedAccessControlList.PublicRead)
            );

            imageList.add(path + fileName);
        }

        return imageList;
    }
}
