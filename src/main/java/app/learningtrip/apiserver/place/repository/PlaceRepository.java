package app.learningtrip.apiserver.place.repository;

import app.learningtrip.apiserver.place.domain.Place;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlaceRepository extends JpaRepository<Place, Long> {
    int countAllBy();
    Place findByName(String name);
}
