package app.learningtrip.apiserver.place.repository;

import app.learningtrip.apiserver.place.domain.PlaceDetailTour;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlaceDetailTourRepository extends JpaRepository<PlaceDetailTour, Long> {

}
