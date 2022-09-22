package app.learningtrip.apiserver.heritage.dto.response;

import java.util.List;
import lombok.Data;

@Data
public class HeritageThumbnailListResponse {
    private List<HeritageThumbnail> heritageThumbnailList;

    public HeritageThumbnailListResponse(List<HeritageThumbnail> heritageThumbnailList) {
        this.heritageThumbnailList = heritageThumbnailList;
    }
}
