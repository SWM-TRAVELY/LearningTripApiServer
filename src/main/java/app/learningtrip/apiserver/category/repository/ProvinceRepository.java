package app.learningtrip.apiserver.category.repository;

import app.learningtrip.apiserver.category.domain.Province;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProvinceRepository extends JpaRepository<Province, Long> {

}
