package app.learningtrip.apiserver.level.repository;

import app.learningtrip.apiserver.level.domain.Level;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface LevelRepository extends JpaRepository<Level, Long> {

  @Query(value = "SELECT l.name FROM Level l "
      +"WHERE l.requiredPoint <= :exp AND l.limitPoint > :exp")
  Optional<String> findLevelByExp(@Param("exp") int exp);
}
