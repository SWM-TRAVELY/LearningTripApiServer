package app.learningtrip.apiserver.dummy_temp;

import java.util.List;
import lombok.Data;

@Data
public class RecommendCourseResponse {
  private List<Course> courseList;

  public RecommendCourseResponse(List<Course> courseList) {
    this.courseList = courseList;
  }
}
