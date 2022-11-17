package app.learningtrip.apiserver.category.repository;

import app.learningtrip.apiserver.category.domain.Grade;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GradeRepository extends JpaRepository<Grade, Long> {
}
