package app.learningtrip.apiserver.dummy_temp;

import app.learningtrip.apiserver.place.dto.response.PlaceThumbnail;
import java.util.ArrayList;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {

  @GetMapping("autocomplete/{keyword}")
  public ResponseEntity<AutoCompleteResponse> autoComplete(@PathVariable("keyword") String keyword){
    AutoCompleteResponse autoCompleteResponse = new AutoCompleteResponse();

    return ResponseEntity.ok().body(autoCompleteResponse);
  }

  @GetMapping("result/{keyword}")
  public ResponseEntity<SearchResultResponse> getSearchResult(@PathVariable("keyword") String keyword){
    SearchResultResponse searchResultResponse = new SearchResultResponse();

    return ResponseEntity.ok().body(searchResultResponse);
  }
}
