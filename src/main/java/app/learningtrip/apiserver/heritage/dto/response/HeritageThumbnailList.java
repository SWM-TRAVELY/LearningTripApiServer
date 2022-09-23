package app.learningtrip.apiserver.heritage.dto.response;

import java.util.List;
import lombok.Data;

@Data
public class HeritageThumbnailList {
    private List<HeritageThumbnail> heritageThumbnailList;

    public HeritageThumbnailList(List<HeritageThumbnail> heritageThumbnailList) {
        this.heritageThumbnailList = heritageThumbnailList;
    }
}
