package app.learningtrip.apiserver.dummy_temp;


import java.util.List;
import lombok.Data;

@Data public class SearchResultResponse {
  private List<PlaceThumbnail> placeThumbnailList;
}
