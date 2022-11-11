package app.learningtrip.apiserver.course.repository;

import app.learningtrip.apiserver.course.domain.CourseRecommend;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseRecommendRepository extends JpaRepository<CourseRecommend, Long> {
    int countAllBy();
}
