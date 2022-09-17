package app.learningtrip.apiserver.course.dto.response;

import java.util.List;
import lombok.Data;

@Data
public class CourseThumbnailListResponse {
    private List<CourseThumbnail> courseThumbnailList;

    public CourseThumbnailListResponse(List<CourseThumbnail> courseThumbnailList) {
        this.courseThumbnailList = courseThumbnailList;
    }

}
