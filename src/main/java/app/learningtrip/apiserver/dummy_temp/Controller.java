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
  public List<PlaceThumbnail> createPlaceList(){
    List<PlaceThumbnail> placeThumbnailList = new ArrayList<PlaceThumbnail>();

    placeThumbnailList.add(new PlaceThumbnail(1));
    placeThumbnailList.add(new PlaceThumbnail(2));
    placeThumbnailList.add(new PlaceThumbnail(3));
    placeThumbnailList.add(new PlaceThumbnail(4));

    return placeThumbnailList;
  }

  public List<Course> createCourseList(){
    List<Course> courseList = new ArrayList<Course>();

    courseList.add(new Course(1));
    courseList.add(new Course(2));

    return courseList;
  }

  @GetMapping("course/recommend")
  public ResponseEntity<RecommendCourseResponse> getRecommendCourse(){
    RecommendCourseResponse recommendCourseResponse = new RecommendCourseResponse(createCourseList());

    return ResponseEntity.ok().body(recommendCourseResponse);
  }

  @GetMapping("nearby/{placeId}")
  public ResponseEntity<NearbyPlaceResponse> getNearbyPlace(@PathVariable("placeId") Long id){
    NearbyPlaceResponse nearbyPlaceResponse = new NearbyPlaceResponse(createPlaceList());

    return ResponseEntity.ok().body(nearbyPlaceResponse);
  }

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
