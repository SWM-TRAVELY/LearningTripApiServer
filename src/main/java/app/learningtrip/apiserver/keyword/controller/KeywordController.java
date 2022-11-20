package app.learningtrip.apiserver.keyword.controller;

import app.learningtrip.apiserver.keyword.domain.Keyword;
import app.learningtrip.apiserver.keyword.dto.response.KeywordRecommendResponse;
import app.learningtrip.apiserver.keyword.service.KeywordService;
import app.learningtrip.apiserver.place.dto.response.PlaceThumbnail;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class KeywordController {

    @Autowired
    private KeywordService keywordService;

    @GetMapping("/home/keyword/recommend")
    public ResponseEntity<List<KeywordRecommendResponse>> getRecommendKeyword() {
        List<KeywordRecommendResponse> keywordList = keywordService.getKeyword();

        return ResponseEntity.ok().body(keywordList);
    }

    @GetMapping("/home/keyword")
    public Keyword getKeyword() {
        return Keyword.builder()
            .keyword("키워드1")
            .imageURL("http://tong.visitkorea.or.kr/cms/resource/01/1945801_image2_1.jpg")
            .build();
    }

    @GetMapping("/keyword/{keyword}")
    public ResponseEntity<List<PlaceThumbnail>> getPlaceByKeyword(@PathVariable(name = "keyword") String keyword) {
        List<PlaceThumbnail> placeThumbnailList = keywordService.getPlace(keyword);

        return ResponseEntity.ok().body(placeThumbnailList);
    }
}
