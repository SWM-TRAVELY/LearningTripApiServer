package app.learningtrip.apiserver.review.service;

import app.learningtrip.apiserver.review.domain.Helpful;
import app.learningtrip.apiserver.review.domain.Review;
import app.learningtrip.apiserver.review.dto.request.HelpfulRequest;
import app.learningtrip.apiserver.review.repository.HelpfulRepository;
import app.learningtrip.apiserver.review.repository.ReviewRepository;
import app.learningtrip.apiserver.user.domain.User;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class HelpfulService {

    private final HelpfulRepository helpfulRepository;
    private final ReviewRepository reviewRepository;

    /**
     * 리뷰 당 도움횟수 조회
     */
    public int helpfulCountByReview(Long review_id) {
        List<Helpful> helpfulList = helpfulRepository.findAllByReviewId(review_id);

        int count = 0;
        for (Helpful helpful : helpfulList) {
            if (helpful.isHelpful()) {
                count++;
            }
        }
        return count;
    }

    /**
     * 도움여부 등록
     */
    public void insert(HelpfulRequest helpfulRequest, User user) {
        // helpful 조회
        helpfulRepository.findByReviewIdAndUserId(helpfulRequest.getReview_id(), user.getId())
            .ifPresent(m -> {
              throw new IllegalStateException("이미 도움 여부를 체크한 리뷰입니다.");
            });

        // review 조회
        Optional<Review> review = reviewRepository.findById(helpfulRequest.getReview_id());
        review.orElseThrow(() -> new NoSuchElementException("존재하지 않은 Review입니다."));

        helpfulRepository.save(Helpful.builder()
            .helpful(helpfulRequest.isHelpful())
            .review(review.get())
            .user(user)
            .build());
    }

    /**
     * 도움여부 삭제
     */
    public void delete(HelpfulRequest helpfulRequest, User user) {
        // helpful 조회
        Optional<Helpful> helpful = helpfulRepository.findByReviewIdAndUserIdAndHelpful(helpfulRequest.getReview_id(), user.getId(),
            helpfulRequest.isHelpful());
        helpful.orElseThrow(() -> new NoSuchElementException("존재하지 않은 helpful이거나 삭제할 수 없는 helpful state입니다."));

        helpfulRepository.delete(helpful.get());
    }
}
