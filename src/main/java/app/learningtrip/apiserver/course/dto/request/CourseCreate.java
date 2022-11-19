package app.learningtrip.apiserver.course.dto.request;

import java.util.List;
import lombok.Data;

@Data
public class CourseCreate {
    private Long Start;

    private Long end;

    private String location;

    private String locationOption;

    private String grade;

    private String gradeOption;

    private List<String> keyword;
}
