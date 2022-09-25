package app.learningtrip.apiserver.heritage.dto.response;

import app.learningtrip.apiserver.heritage.domain.Heritage;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class HeritageResponse {

    private Long id;

    private String name;

    private String imageURL;

    private String description;

    private String type;

    private String category;

    public static HeritageResponse toResponse(Heritage heritage) {
        return HeritageResponse.builder()
            .id(heritage.getId())
            .name(heritage.getName())
            .imageURL(heritage.getImageURL())
            .description(heritage.getDescription())
            .type(heritage.getType())
            .category(heritage.getCategory4())
            .build();
    }
}
