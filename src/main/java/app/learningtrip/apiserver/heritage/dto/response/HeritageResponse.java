package app.learningtrip.apiserver.heritage.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class HeritageResponse {

    private long id;

    private String name;

    private String imageURL;

    private String description;

    private String type;

    private String category;
}
