package app.learningtrip.apiserver.place.dto.response;

import app.learningtrip.apiserver.dummy_temp.PlaceThumbnail;
import java.util.List;
import lombok.Data;

@Data
public class PlaceThumbnailResponse {
    private List<PlaceThumbnail> placeThumbnailList;

    public PlaceThumbnailResponse(List<PlaceThumbnail> placeThumbnailList) {
        this.placeThumbnailList = placeThumbnailList;
    }
}
