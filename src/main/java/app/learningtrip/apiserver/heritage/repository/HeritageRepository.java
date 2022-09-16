package app.learningtrip.apiserver.heritage.repository;

import app.learningtrip.apiserver.heritage.domain.Heritage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HeritageRepository extends JpaRepository<Heritage, Integer> {
}
