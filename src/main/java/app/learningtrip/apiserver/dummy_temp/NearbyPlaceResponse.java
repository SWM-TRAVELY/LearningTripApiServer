package app.learningtrip.apiserver.dummy_temp;

import java.util.List;
import lombok.Data;

@Data
public class NearbyPlaceResponse {
  private List<PlaceThumbnail> placeThumbnailList;

  public NearbyPlaceResponse(List<PlaceThumbnail> placeThumbnailList) {
    this.placeThumbnailList = placeThumbnailList;
  }
}
