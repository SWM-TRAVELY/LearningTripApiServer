package app.learningtrip.apiserver.dummy_temp;

import java.util.List;
import lombok.Data;

@Data
public class RecommendPlaceResponse {
  private List<PlaceThumbnail> placeThumbnailList;

  public RecommendPlaceResponse(List<PlaceThumbnail> placeThumbnailList) {
    this.placeThumbnailList = placeThumbnailList;
  }
}
