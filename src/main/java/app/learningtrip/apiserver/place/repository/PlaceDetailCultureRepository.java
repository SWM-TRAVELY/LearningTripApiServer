package app.learningtrip.apiserver.place.repository;

import app.learningtrip.apiserver.place.domain.PlaceDetailCulture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlaceDetailCultureRepository extends JpaRepository<PlaceDetailCulture, Long> {

}
