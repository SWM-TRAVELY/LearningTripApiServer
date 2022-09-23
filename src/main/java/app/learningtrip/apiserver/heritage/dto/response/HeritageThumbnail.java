package app.learningtrip.apiserver.heritage.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class HeritageThumbnail {
    private long id;

    private String name;

    private String imageURL;
}