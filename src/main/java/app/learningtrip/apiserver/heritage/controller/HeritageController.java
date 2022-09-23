package app.learningtrip.apiserver.heritage.controller;

import app.learningtrip.apiserver.heritage.dto.response.HeritageResponse;
import app.learningtrip.apiserver.heritage.dto.response.HeritageThumbnail;
import app.learningtrip.apiserver.heritage.service.HeritageService;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/heritage")
@RequiredArgsConstructor
public class HeritageController {

    @Autowired
    private HeritageService heritageService;

    @GetMapping("/{heritage_id}")
    public ResponseEntity info(@PathVariable(name = "heritage_id") Long heritage_id) {
        try {
            Optional<HeritageResponse> heritageResponse = heritageService.getInfo(heritage_id);
            return ResponseEntity.ok().body(heritageResponse);
        } catch (NoSuchElementException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }

    @GetMapping("/related/{place_id}")
    public ResponseEntity related(@PathVariable(name = "place_id") Long place_id, Model model) {
        Optional<List<HeritageThumbnail>> heritageThumbnailList = heritageService.getHeritages(place_id);
        model.addAttribute("heritageThumbnailList", heritageThumbnailList);
        return ResponseEntity.ok().body(model);
    }
}
