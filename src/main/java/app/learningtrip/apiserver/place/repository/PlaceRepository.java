package app.learningtrip.apiserver.place.repository;

import app.learningtrip.apiserver.place.domain.Place;
import app.learningtrip.apiserver.search.dto.PlaceSearchResult;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PlaceRepository extends JpaRepository<Place, Long> {
    int countAllBy();
    Place findByName(String name);
    
    /**
     *
     * @param lat 경도
     * @param lng 위도
     * @param pageable 쿼리 row 수
     * @return 좌표로 부터 반경 5000m 이내 관광지 리스트
     */
    @Query(value = "select p from Place p "
        + "where ST_Distance_Sphere(point(p.longitude,p.latitude), point(:x,:y)) < 5000 "
        + "order by ST_Distance_Sphere(point(p.longitude,p.latitude), point(:x,:y))")
    Optional<List<Place>> findPlacesByDistance(@Param("x") double lng, @Param("y") double lat,
        Pageable pageable);

    @Query(value = "select p.name from Place p where p.name like %:keyword% order by p.name")
    Optional<List<String>> findNamesByNameLike(@Param("keyword") String keyword);

    @Query(value = "select new app.learningtrip.apiserver.search.dto.PlaceSearchResult(p.id, p.name, "
        + "p.address, p.imageURL1) "
        + "from Place p where p.name like %:keyword% order by p.name")
    Optional<List<PlaceSearchResult>> findPlacesByNameLike(@Param("keyword") String keyword);
}