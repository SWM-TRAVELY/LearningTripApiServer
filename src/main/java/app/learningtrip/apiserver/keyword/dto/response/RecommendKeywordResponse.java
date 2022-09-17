package app.learningtrip.apiserver.keyword.dto.response;

import app.learningtrip.apiserver.keyword.domain.Keyword;
import java.util.List;
import lombok.Data;

@Data
public class RecommendKeywordResponse {
    private List<Keyword> keywordList;

    public RecommendKeywordResponse(List<Keyword> keywordList) {
        this.keywordList = keywordList;
    }
}
