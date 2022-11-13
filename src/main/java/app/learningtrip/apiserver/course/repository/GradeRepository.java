package app.learningtrip.apiserver.course.repository;

import app.learningtrip.apiserver.course.domain.Grade;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GradeRepository extends JpaRepository<Grade, Long> {
}
