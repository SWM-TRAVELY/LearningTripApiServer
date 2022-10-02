package app.learningtrip.apiserver.search.service;

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
    return sortSearchResultList(placeRepository.findPlacesByNameLike(keyword)
        .orElseThrow(null), keyword);
  }
}
