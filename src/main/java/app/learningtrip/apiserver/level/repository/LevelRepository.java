package app.learningtrip.apiserver.level.repository;

import app.learningtrip.apiserver.level.domain.Level;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LevelRepository extends JpaRepository<Level, Long> {

}
