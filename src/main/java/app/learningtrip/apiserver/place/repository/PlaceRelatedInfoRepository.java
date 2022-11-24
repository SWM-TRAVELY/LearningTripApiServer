package app.learningtrip.apiserver.place.repository;

import app.learningtrip.apiserver.place.domain.PlaceRelatedInfo;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PlaceRelatedInfoRepository extends JpaRepository<PlaceRelatedInfo, Long> {
  @Query(value = "SELECT p FROM PlaceRelatedInfo p WHERE p.province = :province AND p.city = :city")
  List<PlaceRelatedInfo> findAllByProvinceCity(@Param("province") String province, @Param("city") String city);
}
