package app.learningtrip.apiserver.place.controller;

import app.learningtrip.apiserver.place.dto.response.PlaceResponse;
import app.learningtrip.apiserver.place.dto.response.PlaceThumbnailListResponse;
import app.learningtrip.apiserver.place.service.PlaceService;
import java.rmi.NoSuchObjectException;
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

    @GetMapping("/{place_id}")
    public ResponseEntity info(@PathVariable(name = "place_id") long place_id) {
        try {
            Optional<PlaceResponse> placeResponse = placeService.getInfo(place_id);

            return ResponseEntity.ok().body(placeResponse);
        } catch (NoSuchElementException e) {

            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (NoSuchObjectException e) {

            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (NoSuchFieldError e) {

            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/related/{place_id}")
    public ResponseEntity<Optional<PlaceThumbnailListResponse>> similarPlace(@PathVariable(name = "place_id") long place_id) {
        Optional<PlaceThumbnailListResponse> placeThumbnailResponse = Optional.ofNullable(placeService.similarPlaceDummy(place_id));

        return ResponseEntity.ok().body(placeThumbnailResponse);
    }
}
