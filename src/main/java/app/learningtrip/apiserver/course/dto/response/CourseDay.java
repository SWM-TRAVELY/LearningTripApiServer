package app.learningtrip.apiserver.course.dto.response;

import java.util.List;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter @Setter @Builder
public class CourseDay {

    int day;

    List<CoursePlaceResponse> coursePlaceResponseList;
}
