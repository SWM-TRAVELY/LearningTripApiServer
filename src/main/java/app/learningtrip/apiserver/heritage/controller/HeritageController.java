package app.learningtrip.apiserver.heritage.controller;

import app.learningtrip.apiserver.heritage.domain.Heritage;
import app.learningtrip.apiserver.heritage.dto.response.HeritageResponse;
import app.learningtrip.apiserver.heritage.dto.response.HeritageThumbnail;
import app.learningtrip.apiserver.heritage.dto.response.HeritageThumbnailListResponse;
import app.learningtrip.apiserver.heritage.service.HeritageService;
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
public class HeritageController {

    @Autowired
    private HeritageService heritageService;

    @GetMapping("/heritage/sieun/{heritage_id}")
    public ResponseEntity<Optional<Heritage>> testHeritage(@PathVariable(name = "heritage_id") long heritage_id) {
        Optional<Heritage> heritage = heritageService.findOne(heritage_id);
        return ResponseEntity.ok().body(heritage);
    }

    @GetMapping("/heritage/{heritage_id}")
    public ResponseEntity<Optional<HeritageResponse>> info(@PathVariable(name = "heritage_id") Long heritage_id) {
        Optional<HeritageResponse> heritageResponse = Optional.ofNullable(heritageService.heritageInfoDummy(heritage_id));
        return ResponseEntity.ok().body(heritageResponse);
    }

    @GetMapping("/heritage/related/{place_id}")
    public ResponseEntity<Optional<HeritageThumbnailListResponse>> related(@PathVariable(name = "place_id") Long place_id) {
        Optional<HeritageThumbnailListResponse> heritageThumbnailListResponse = Optional.ofNullable(heritageService.findHeritageDummy(place_id));
        return ResponseEntity.ok().body(heritageThumbnailListResponse);
    }
}
