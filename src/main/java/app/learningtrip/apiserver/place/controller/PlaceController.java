package app.learningtrip.apiserver.place.controller;

import app.learningtrip.apiserver.dummy_temp.PlaceThumbnail;
import app.learningtrip.apiserver.place.domain.Place;
import app.learningtrip.apiserver.place.dto.response.PlaceDetailCultureResponse;
import app.learningtrip.apiserver.place.dto.response.PlaceDetailTourResponse;
import app.learningtrip.apiserver.place.dto.response.PlaceResponse;
import app.learningtrip.apiserver.place.dto.response.PlaceThumbnailResponse;
import app.learningtrip.apiserver.place.service.PlaceService;
import java.util.ArrayList;
import java.util.List;
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
    public ResponseEntity<Optional<PlaceThumbnailResponse>> similarPlace(@PathVariable(name = "place_id") long place_id) {
        Optional<PlaceThumbnailResponse> placeThumbnailResponse = Optional.ofNullable(placeService.similarPlaceDummy(place_id));
        return ResponseEntity.ok().body(placeThumbnailResponse);
    }
}
