package app.learningtrip.apiserver.course.repository;

import app.learningtrip.apiserver.course.domain.Course;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {
    int countAllBy();
    List<Course> findAllByUserId(Long user_id);
}
