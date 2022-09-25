package app.learningtrip.apiserver.keyword.repository;

import app.learningtrip.apiserver.keyword.domain.Keyword;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KeywordRepository extends JpaRepository<Keyword, Long> {
    List<Keyword> findTop10ByOrderByCountDesc();
}
