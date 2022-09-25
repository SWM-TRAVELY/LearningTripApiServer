package app.learningtrip.apiserver.place.controller;

import app.learningtrip.apiserver.place.dto.response.PlaceResponse;
import app.learningtrip.apiserver.place.dto.response.PlaceThumbnail;
import app.learningtrip.apiserver.place.service.PlaceService;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
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
    public ResponseEntity recommendPlace(){
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
    public ResponseEntity<List<PlaceThumbnail>> similarPlace(@PathVariable(name = "place_id") long place_id) {
        List<PlaceThumbnail> placeThumbnailResponse = placeService.getSimilarDummy(place_id);

        return ResponseEntity.ok().body(placeThumbnailResponse);
    }

    @GetMapping("nearby/{placeId}")
    public ResponseEntity nearbyPlace(@PathVariable("placeId") Long place_id){
        List<PlaceThumbnail> placeThumbnailList = placeService.getNearbyDummy(place_id);

        return ResponseEntity.ok().body(placeThumbnailList);
    }
}
