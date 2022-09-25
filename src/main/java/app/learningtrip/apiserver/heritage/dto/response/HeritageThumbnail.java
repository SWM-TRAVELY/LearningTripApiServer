package app.learningtrip.apiserver.heritage.dto.response;

import app.learningtrip.apiserver.heritage.domain.Heritage;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class HeritageThumbnail {
    private Long id;

    private String name;

    private String imageURL;

    public static HeritageThumbnail toThumbnail(Heritage heritage) {
        return HeritageThumbnail.builder()
            .id(heritage.getId())
            .name(heritage.getName())
            .imageURL(heritage.getImageURL())
            .build();
    }
}
