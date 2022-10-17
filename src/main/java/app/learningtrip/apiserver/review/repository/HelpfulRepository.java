package app.learningtrip.apiserver.review.repository;

import app.learningtrip.apiserver.review.domain.Helpful;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HelpfulRepository extends JpaRepository<Helpful, Long> {

  Optional<Helpful> findByReviewIdAndUserId(Long review_id, Long user_id);

  List<Helpful> findAllByReviewId(Long review_id);

  Optional<Helpful> findByReviewIdAndUserIdAndHelpful(Long review_id, Long user_id, boolean helpful);
}
