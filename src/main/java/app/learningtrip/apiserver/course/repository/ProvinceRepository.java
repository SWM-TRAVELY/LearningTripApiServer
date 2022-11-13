package app.learningtrip.apiserver.course.repository;

import app.learningtrip.apiserver.course.domain.Province;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProvinceRepository extends JpaRepository<Province, Long> {

}
