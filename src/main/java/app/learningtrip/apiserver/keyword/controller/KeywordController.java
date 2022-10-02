package app.learningtrip.apiserver.keyword.controller;

import app.learningtrip.apiserver.keyword.domain.Keyword;
import app.learningtrip.apiserver.keyword.dto.response.RecommendKeywordResponse;
import app.learningtrip.apiserver.keyword.service.KeywordService;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class KeywordController {

    @Autowired
    private KeywordService keywordService;
/*
    @GetMapping("/home/keyword/recommend")
    public ResponseEntity<List<Keyword>> getRecommendKeyword() {
        List<Keyword> keywordList = keywordService.getKeyword();

        return ResponseEntity.ok().body(keywordList);
    }
*/
    @GetMapping("/home/keyword/recommend")
    public ResponseEntity<List<Keyword>> getRecommendKeywordDummy() {
        List<Keyword> keywordList = new ArrayList<>();

        keywordList.add(Keyword.builder().name("경주").imageURL("https://youimg1.tripcdn.com/target/0106j1200093s90ih82FB_C_640_320_R5_Q70.jpg_.webp?proc=source%2Ftrip").count(0).build());
        keywordList.add(Keyword.builder().name("한양").imageURL("https://seoulcitywall.seoul.go.kr/image.do?file=/wallcourse/info/20211101073653083.jpg").count(0).build());
        return ResponseEntity.ok().body(keywordList);
    }

    @GetMapping("/home/keyword")
    public Keyword getKeyword() {
        return Keyword.builder()
            .name("키워드1")
            .imageURL("http://tong.visitkorea.or.kr/cms/resource/01/1945801_image2_1.jpg")
            .build();
    }
}
