package app.learningtrip.apiserver.course.repository;

import app.learningtrip.apiserver.course.domain.CourseUser;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseUserRepository extends JpaRepository<CourseUser, Long> {

    List<CourseUser> findAllByUserId(Long user_id);
}
