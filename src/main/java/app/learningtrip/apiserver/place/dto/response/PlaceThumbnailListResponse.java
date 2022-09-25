package app.learningtrip.apiserver.place.dto.response;

import java.util.List;
import lombok.Data;

@Data
public class PlaceThumbnailListResponse {
    private List<PlaceThumbnail> placeThumbnailList;

    public PlaceThumbnailListResponse(List<PlaceThumbnail> placeThumbnailList) {
        this.placeThumbnailList = placeThumbnailList;
    }
}
