package app.learningtrip.apiserver.heritage.repository;

import app.learningtrip.apiserver.heritage.domain.Heritage;
import app.learningtrip.apiserver.heritage.dto.response.HeritageThumbnail;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface HeritageRepository extends JpaRepository<Heritage, Long> {
    @Query("select h from Heritage h where h.place.id = :id")
    List<Heritage> findMatchingHeritages(@Param("id") Long id);
}
