package app.learningtrip.apiserver.keyword.dto.response;

import app.learningtrip.apiserver.keyword.domain.Keyword;
import java.util.ArrayList;
import java.util.List;
import lombok.Builder;
import lombok.Data;

@Data @Builder
public class KeywordRecommendResponse {
    private String name;

    private String imageURL;

    private Integer count;

    public static KeywordRecommendResponse toResponse(Keyword keyword) {
        return KeywordRecommendResponse.builder()
            .name(keyword.getKeyword())
            .imageURL(keyword.getImageURL())
            .count(keyword.getPlaceCount() + keyword.getSearchCount())
            .build();
    }
}
