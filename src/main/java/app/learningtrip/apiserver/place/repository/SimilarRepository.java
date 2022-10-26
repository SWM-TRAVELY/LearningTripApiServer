package app.learningtrip.apiserver.place.repository;

import app.learningtrip.apiserver.place.domain.Similar;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SimilarRepository extends JpaRepository<Similar, Long> {

  Optional<Similar> findByPlaceId(long place_id);
}
