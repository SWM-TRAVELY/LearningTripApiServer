package app.learningtrip.apiserver.place.controller;

import app.learningtrip.apiserver.place.dto.response.PlaceResponse;
import app.learningtrip.apiserver.place.dto.response.PlaceThumbnail;
import app.learningtrip.apiserver.place.dto.response.PlaceThumbnailListResponse;
import app.learningtrip.apiserver.place.service.PlaceService;
import java.rmi.NoSuchObjectException;
import java.util.ArrayList;
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
@RequestMapping("/place")
@RequiredArgsConstructor
public class PlaceController {

    @Autowired
    private PlaceService placeService;

    @GetMapping("/recommend")
    public ResponseEntity getRecommendPlace(){
        List<PlaceThumbnail> placeThumbnailList = new ArrayList<PlaceThumbnail>();

        placeThumbnailList.add(new PlaceThumbnail(1));
        placeThumbnailList.add(new PlaceThumbnail(2));
        placeThumbnailList.add(new PlaceThumbnail(3));
        placeThumbnailList.add(new PlaceThumbnail(4));

        return ResponseEntity.ok().body(placeThumbnailList);
    }

    @GetMapping("/{place_id}")
    public ResponseEntity info(@PathVariable(name = "place_id") long place_id) {
        Optional<PlaceResponse> placeResponse = placeService.getInfoDummy(place_id);
        return ResponseEntity.ok().body(placeResponse);
    }

    @GetMapping("/related/{place_id}")
    public ResponseEntity<Optional<PlaceThumbnailListResponse>> similarPlace(@PathVariable(name = "place_id") long place_id) {
        Optional<PlaceThumbnailListResponse> placeThumbnailResponse = Optional.ofNullable(placeService.similarPlaceDummy(place_id));

        return ResponseEntity.ok().body(placeThumbnailResponse);
    }
}
