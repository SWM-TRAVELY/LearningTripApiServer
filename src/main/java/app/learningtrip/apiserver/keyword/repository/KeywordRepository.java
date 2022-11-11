package app.learningtrip.apiserver.keyword.repository;

import app.learningtrip.apiserver.keyword.domain.Keyword;
import app.learningtrip.apiserver.place.domain.Place;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface KeywordRepository extends JpaRepository<Keyword, Long> {

    @Query(value = "SELECT * FROM keyword k "
        + "ORDER BY (k.place_count + k.search_count) DESC "
        + "LIMIT 10", nativeQuery = true)
    List<Keyword> findTop10ByOrderByPlaceCountPlusSearchCountDesc();
}
