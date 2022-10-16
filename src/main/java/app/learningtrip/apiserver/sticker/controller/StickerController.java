package app.learningtrip.apiserver.sticker.controller;

import app.learningtrip.apiserver.configuration.auth.PrincipalDetails;
import app.learningtrip.apiserver.sticker.service.StickerService;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class StickerController {

    private final StickerService stickerService;

    @GetMapping("/sticker")
    public ResponseEntity getSticker(@AuthenticationPrincipal PrincipalDetails user) {
        List<String> placeImageList = stickerService.get(user.getUser());

        return ResponseEntity.ok().body(placeImageList);
    }

    @PostMapping("/sticker/{place_id}")
    public ResponseEntity insertSticker(@PathVariable(name = "place_id") long place_id, @AuthenticationPrincipal PrincipalDetails user) {
        stickerService.insert(place_id, user.getUser());

        return ResponseEntity.ok().body(200);
    }

}
