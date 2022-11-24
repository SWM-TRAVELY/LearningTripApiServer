package app.learningtrip.apiserver.category.repository;

import app.learningtrip.apiserver.category.domain.Grade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface GradeRepository extends JpaRepository<Grade, Long> {
  @Query(value = "SELECT g.id FROM Grade g WHERE g.school = :school AND g.grade = :grade")
  Long findIdBySchoolGrade(@Param("school") String school, @Param("grade") String grade);
}
