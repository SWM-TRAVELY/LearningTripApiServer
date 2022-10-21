package app.learningtrip.apiserver.place.controller;

import app.learningtrip.apiserver.place.dto.response.PlaceResponse;
import app.learningtrip.apiserver.place.dto.response.PlaceThumbnail;
import app.learningtrip.apiserver.place.service.PlaceService;
import io.swagger.annotations.ApiOperation;
import java.rmi.NoSuchObjectException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PlaceController {

    private final PlaceService placeService;

    @GetMapping("sieun/test")
    public ResponseEntity testSave() {
        placeService.setSeoulTextbookPlace();
        //placeService.setGyeongjuTextbookPlace();

        return ResponseEntity.ok().body(null);
    }

    @GetMapping("/home/place/recommend")
    @ApiOperation(value = "추천 관광지 조회", notes = "추천하는 관광지를 조회한다.")
    public ResponseEntity recommendPlace(){
        List<PlaceThumbnail> placeThumbnailList = placeService.getRecommend();

        return ResponseEntity.ok().body(placeThumbnailList);
    }

    @GetMapping("/place/{place_id}")
    @ApiOperation(value = "관광지 정보 조회", notes = "관광지 정보를 조회한다.")
    public ResponseEntity info(@PathVariable(name = "place_id") long place_id) {
        try {
            Optional<PlaceResponse> placeResponse = placeService.getInfo(place_id);
            return ResponseEntity.ok().body(placeResponse);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (NoSuchObjectException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/place/related/{place_id}")
    @ApiOperation(value = "유사 관광지 조회", notes = "특정 관광지와 유사한 관광지를 조회한다.")
    public ResponseEntity<List<PlaceThumbnail>> similarPlace(@PathVariable(name = "place_id") long place_id) {
        List<PlaceThumbnail> placeThumbnailResponse = placeService.getSimilar(place_id);
        Collections.shuffle(placeThumbnailResponse);

        return ResponseEntity.ok().body(placeThumbnailResponse);
    }

    @GetMapping("/place/nearby/{placeId}/dummy")
    public ResponseEntity nearbyPlaceDummy(@PathVariable("placeId") Long place_id){
        List<PlaceThumbnail> placeThumbnailList = placeService.getNearbyDummy(place_id);

        return ResponseEntity.ok().body(placeThumbnailList);
    }

    @GetMapping("/place/nearby/{placeId}")
    @ApiOperation(value = "주변 관광지 조회", notes = "특정 관광지의 주변에 있는 관광지를 조회한다.")
    public ResponseEntity<List<PlaceThumbnail>> nearbyPlace(@PathVariable("placeId") Long place_id){
        return ResponseEntity.ok().body(placeService.getNearby(place_id));
    }

    @GetMapping("search/place/{word}")
    public ResponseEntity searchPlace(@PathVariable("word") String word) {
        List<PlaceThumbnail> placeThumbnailList = placeService.getSearchDummy(word);

        return ResponseEntity.ok().body(placeThumbnailList);
    }

    @GetMapping("search/keyword/{word}")
    public ResponseEntity completeWord(@PathVariable("word") String word) {
        List<String> stringList = placeService.getCompleteWordDummy(word);

        return ResponseEntity.ok().body(stringList);
    }
}
