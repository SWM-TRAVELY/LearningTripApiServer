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
        + "ORDER BY (k.place_count + (k.search_count * 10)) DESC "
        + "LIMIT 10", nativeQuery = true)
    List<Keyword> findTop10ByOrderByPlaceCountPlusSearchCountDesc();

    Optional<Keyword> findByKeyword(String keyword);

    @Query(value = "select k.keyword from Keyword k where k.keyword like %:keyword% order by k.keyword")
    List<String> findKeywordsByNameLike(@Param("keyword") String keyword);
}
