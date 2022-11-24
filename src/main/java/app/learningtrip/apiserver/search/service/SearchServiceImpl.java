package app.learningtrip.apiserver.search.service;

import app.learningtrip.apiserver.category.repository.GradeRepository;
import app.learningtrip.apiserver.place.domain.PlaceRelatedInfo;
import app.learningtrip.apiserver.place.repository.PlaceRelatedInfoRepository;
import app.learningtrip.apiserver.place.repository.PlaceRepository;
import app.learningtrip.apiserver.search.dto.PlaceSearchResult;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class SearchServiceImpl implements SearchService {

  private final PlaceRepository placeRepository;

  private final PlaceRelatedInfoRepository placeRelatedInfoRepository;

  private final GradeRepository gradeRepository;

  public List<String> sortSearchList(List<String> searchList, String keyword){
    List<String> sortedList = new ArrayList<>();
    List<String> notStartsWithList = new ArrayList<>();

    searchList.forEach(word -> {
      if(word.startsWith(keyword)){
        sortedList.add(word);
      }
      else{
        notStartsWithList.add(word);
      }
    });

    sortedList.addAll(notStartsWithList);

    if(sortedList.size() > 10) {
      return sortedList.subList(0,10);
    }
    else {
      return sortedList.subList(0,sortedList.size());
    }
  }

  public List<PlaceSearchResult> sortSearchResultList(List<PlaceSearchResult> searchList, String keyword){
    List<PlaceSearchResult> sortedList = new ArrayList<>();
    List<PlaceSearchResult> notStartsWithList = new ArrayList<>();

    searchList.forEach(place -> {
      if(place.getName().startsWith(keyword)){
        sortedList.add(place);
      }
      else{
        notStartsWithList.add(place);
      }
    });

    sortedList.addAll(notStartsWithList);

    if(sortedList.size() > 10) {
      return sortedList.subList(0,10);
    }
    else {
      return sortedList.subList(0,sortedList.size());
    }
  }

  @Override
  public List<String> autoComplete(String keyword) {
    return sortSearchList(placeRepository.findNamesByNameLike(keyword)
        .orElse(new ArrayList<String>()), keyword);
  }

  @Override
  public List<PlaceSearchResult> searchResult(String keyword) {
    List<String> reservedKeyword = new ArrayList<>();
    reservedKeyword.add("어린이 어린이");
    reservedKeyword.add("초등학교 1학년");
    reservedKeyword.add("초등학교 2학년");
    reservedKeyword.add("초등학교 3학년");
    reservedKeyword.add("초등학교 4학년");
    reservedKeyword.add("초등학교 5학년");
    reservedKeyword.add("초등학교 6학년");
    reservedKeyword.add("중학교 1학년");
    reservedKeyword.add("중학교 2학년");
    reservedKeyword.add("중학교 3학년");
    reservedKeyword.add("고등학교 1학년");
    reservedKeyword.add("고등학교 2학년");
    reservedKeyword.add("고등학교 3학년");

    if(reservedKeyword.contains(keyword)){
      int gradeId = gradeRepository.findIdBySchoolGrade(List.of(keyword.split(" ")).get(0),List.of(keyword.split(" ")).get(1)).intValue();

      List<PlaceRelatedInfo> placeRelatedInfoList = filterGrade(placeRelatedInfoRepository.findAll(), gradeId);
      List<PlaceSearchResult> placeSearchList = new ArrayList<>();

      placeRelatedInfoList.forEach(place -> {
        placeSearchList.add(placeRepository.findPlaceById(place.getId()));
      });

      return placeSearchList;
    }

    return sortSearchResultList(placeRepository.findPlacesByNameLike(keyword)
        .orElseThrow(null), keyword);
  }


  private boolean checkBitmask(int A, int k){
    return (A & (1 << k)) == (1 << k);
  }

  private List<PlaceRelatedInfo> filterGrade(List<PlaceRelatedInfo> placeList, int grade){
    List<PlaceRelatedInfo> filteredPlaceList = new ArrayList<>();

    placeList.forEach(place -> {
      if(checkBitmask(place.getGrades(), grade)){
        filteredPlaceList.add(place);
      }
    });

    return filteredPlaceList;
  }
}
