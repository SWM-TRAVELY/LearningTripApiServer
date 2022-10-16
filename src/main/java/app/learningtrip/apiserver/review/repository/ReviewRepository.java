package app.learningtrip.apiserver.review.repository;

import app.learningtrip.apiserver.review.domain.Review;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

  void deleteById(Long id);

  List<Review> findAllByPlaceId(Long place_id);

  List<Review> findAllByUserId(Long user_id);

  Optional<Review> findByPlaceIdAndUserId(Long place_id, Long user_id);
}
