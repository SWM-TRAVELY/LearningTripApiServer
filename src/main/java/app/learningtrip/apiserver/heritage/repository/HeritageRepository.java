package app.learningtrip.apiserver.heritage.repository;

import app.learningtrip.apiserver.heritage.domain.Heritage;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HeritageRepository extends JpaRepository<Heritage, Long> {
    List<Heritage> findAllByPlaceId(Long id);
}
