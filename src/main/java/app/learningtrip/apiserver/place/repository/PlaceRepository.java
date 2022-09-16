package app.learningtrip.apiserver.place.repository;

import app.learningtrip.apiserver.place.domain.Place;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PlaceRepository extends JpaRepository<Place, Integer> {
}
