package app.learningtrip.apiserver.course.dto.response;

import java.util.List;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter @Setter @Builder
public class CourseResponse {

  private long id;

  private String name;

  private List<CourseDay> courseDayList;
}
