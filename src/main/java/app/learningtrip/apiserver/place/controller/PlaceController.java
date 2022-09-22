package app.learningtrip.apiserver.place.controller;

import app.learningtrip.apiserver.place.dto.response.PlaceResponse;
import app.learningtrip.apiserver.place.dto.response.PlaceThumbnailListResponse;
import app.learningtrip.apiserver.place.service.PlaceService;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PlaceController {

    @Autowired
    private PlaceService placeService;

    @GetMapping("/place/{place_id}")
    public ResponseEntity<Optional<PlaceResponse>> getInfo(@PathVariable(name = "place_id") long place_id) {
        Optional<PlaceResponse> placeResponse = Optional.ofNullable(placeService.findPlaceDummy(place_id));
        return ResponseEntity.ok().body(placeResponse);
    }

    @GetMapping("/place/related/{place_id}")
    public ResponseEntity<Optional<PlaceThumbnailListResponse>> similarPlace(@PathVariable(name = "place_id") long place_id) {
        Optional<PlaceThumbnailListResponse> placeThumbnailResponse = Optional.ofNullable(placeService.similarPlaceDummy(place_id));
        return ResponseEntity.ok().body(placeThumbnailResponse);
    }
}
