package app.learningtrip.apiserver.course.repository;

import app.learningtrip.apiserver.course.domain.CoursePlaceRecommend;
import app.learningtrip.apiserver.course.domain.CoursePlaceUser;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CoursePlaceUserRepository extends JpaRepository<CoursePlaceUser, Long> {

    void deleteAllByCourseId(Long course_id);

    List<CoursePlaceUser> findAllByCourseIdOrderByDayAscSequenceAsc(Long course_id);
}
