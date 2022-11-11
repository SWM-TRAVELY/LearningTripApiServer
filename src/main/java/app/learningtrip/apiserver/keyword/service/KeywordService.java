package app.learningtrip.apiserver.keyword.service;

import app.learningtrip.apiserver.keyword.domain.Keyword;
import app.learningtrip.apiserver.keyword.dto.response.KeywordRecommendResponse;
import app.learningtrip.apiserver.keyword.repository.KeywordRepository;
import app.learningtrip.apiserver.place.domain.Place;
import app.learningtrip.apiserver.place.dto.response.PlaceThumbnail;
import app.learningtrip.apiserver.place.repository.PlaceRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class KeywordService {

    private final KeywordRepository keywordRepository;
    private final PlaceRepository placeRepository;

  /**
   * 추천 keyword
   */
    public List<KeywordRecommendResponse> getKeyword() {
        List<Keyword> keywordList = keywordRepository.findTop10ByOrderByPlaceCountPlusSearchCountDesc();

        List<KeywordRecommendResponse> keywordRecommendResponseList = new ArrayList<KeywordRecommendResponse>();
        for (Keyword keyword : keywordList) {
            keywordRecommendResponseList.add(KeywordRecommendResponse.toResponse(keyword));
        }

        return keywordRecommendResponseList;
    }

    /**
     * keyword가 포함된 Place 조회
     */
    public List<PlaceThumbnail> getPlace(String keyword) {
        Optional<Keyword> keywordPlace = keywordRepository.findByKeyword(keyword);

        System.out.println(keywordPlace);

        List<String> place_ids = List.of(keywordPlace.get().getPlaces().split(","));

        List<PlaceThumbnail> placeThumbnailList = new ArrayList<PlaceThumbnail>();
        for (String place_id : place_ids) {
            Optional<Place> place = placeRepository.findById(Long.valueOf(place_id));
            placeThumbnailList.add(PlaceThumbnail.toThumbnail(place.get()));
        }

        // keyword 조회 시 search_count 값 상승
        keywordPlace.get().setSearchCount(keywordPlace.get().getSearchCount() + 1);
        keywordRepository.save(keywordPlace.get());

        return placeThumbnailList;
    }

  /**
   * Dummy Data
   */
//    public List<Keyword> getKeywordDummy() {
//        List<Keyword> keywordList = new ArrayList<Keyword>();
//        keywordList.add(Keyword.builder()
//            .keyword("키워드1")
//            .imageURL("http://tong.visitkorea.or.kr/cms/resource/01/1945801_image2_1.jpg")
//            .count(0)
//            .build());
//        keywordList.add(Keyword.builder()
//          .name("키워드2")
//          .imageURL("http://tong.visitkorea.or.kr/cms/resource/01/1945801_image2_1.jpg")
//          .count(0)
//          .build());
//
//      return keywordList;
//    }
}
