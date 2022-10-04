package app.learningtrip.apiserver.search.service;

import app.learningtrip.apiserver.search.dto.PlaceSearchResult;
import java.util.List;

public interface SearchService {
  List<String> autoComplete(String keyword);

  List<PlaceSearchResult> searchResult(String keyword);
}
