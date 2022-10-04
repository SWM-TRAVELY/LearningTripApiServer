package app.learningtrip.apiserver.keyword.service;

import app.learningtrip.apiserver.keyword.domain.Keyword;
import app.learningtrip.apiserver.keyword.repository.KeywordRepository;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class KeywordService {

    private final KeywordRepository keywordRepository;

  /**
   * 추천 keyword
   */
    public List<Keyword> getKeyword() {
        return keywordRepository.findTop10ByOrderByCountDesc();
    }

  /**
   * Dummy Data
   */
    public List<Keyword> getKeywordDummy() {
        List<Keyword> keywordList = new ArrayList<Keyword>();
        keywordList.add(Keyword.builder()
            .name("키워드1")
            .imageURL("http://tong.visitkorea.or.kr/cms/resource/01/1945801_image2_1.jpg")
            .count(0)
            .build());
        keywordList.add(Keyword.builder()
          .name("키워드2")
          .imageURL("http://tong.visitkorea.or.kr/cms/resource/01/1945801_image2_1.jpg")
          .count(0)
          .build());

      return keywordList;
    }
}
