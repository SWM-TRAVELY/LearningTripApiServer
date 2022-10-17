package app.learningtrip.apiserver.course.dto.request;

import java.util.List;
import lombok.Data;

@Data
public class CourseDto {

    private long id;

    private String name;

    private Long user_id;

    private List<Integer> placeList;
}
