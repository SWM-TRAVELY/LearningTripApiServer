package app.learningtrip.apiserver.keyword.controller;

import app.learningtrip.apiserver.keyword.domain.Keyword;
import app.learningtrip.apiserver.keyword.dto.response.RecommendKeywordResponse;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class KeywordController {

    @GetMapping("/home/keyword/recommend")
    public ResponseEntity<RecommendKeywordResponse> getRecommendKeyword() {
        List<Keyword> keywordList = new ArrayList<Keyword>();
        keywordList.add(Keyword.builder()
            .name("키워드1")
            .imageURL("http://tong.visitkorea.or.kr/cms/resource/01/1945801_image2_1.jpg")
            .build());
        keywordList.add(Keyword.builder()
            .name("키워드2")
            .imageURL("http://tong.visitkorea.or.kr/cms/resource/01/1945801_image2_1.jpg")
            .build());

        RecommendKeywordResponse recommendKeywordResponse = new RecommendKeywordResponse(keywordList);

        return ResponseEntity.ok().body(recommendKeywordResponse);
    }

    @GetMapping("/home/keyword")
    public Keyword getKeyword() {
        return Keyword.builder()
            .name("키워드1")
            .imageURL("http://tong.visitkorea.or.kr/cms/resource/01/1945801_image2_1.jpg")
            .build();
    }
}
