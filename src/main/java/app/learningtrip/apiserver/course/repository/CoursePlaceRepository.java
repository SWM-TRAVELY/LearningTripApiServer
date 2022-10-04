package app.learningtrip.apiserver.course.repository;

import app.learningtrip.apiserver.course.domain.CoursePlace;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CoursePlaceRepository extends JpaRepository<CoursePlace, Long> {
    List<CoursePlace> findAllByCourseIdOrderByDayAscSequenceAsc(Long course_id);
}
