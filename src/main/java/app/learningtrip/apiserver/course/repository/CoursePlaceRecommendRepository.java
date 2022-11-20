package app.learningtrip.apiserver.course.repository;

import app.learningtrip.apiserver.course.domain.CoursePlaceRecommend;
import app.learningtrip.apiserver.course.domain.CoursePlaceUser;
import app.learningtrip.apiserver.course.domain.CourseRecommend;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CoursePlaceRecommendRepository extends JpaRepository<CoursePlaceRecommend, Long> {

    void deleteAllByCourseId(Long course_id);

    List<CoursePlaceRecommend> findAllByCourseIdOrderByDayAscSequenceAsc(Long course_id);
}
