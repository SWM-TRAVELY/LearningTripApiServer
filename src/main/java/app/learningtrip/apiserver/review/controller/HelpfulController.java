package app.learningtrip.apiserver.review.controller;

import app.learningtrip.apiserver.configuration.auth.PrincipalDetails;
import app.learningtrip.apiserver.review.dto.request.HelpfulRequest;
import app.learningtrip.apiserver.review.service.HelpfulService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class HelpfulController {

    private final HelpfulService helpfulService;

    @PostMapping("/helpful")
    public ResponseEntity insertHelpful(@RequestBody HelpfulRequest helpfulRequest, @AuthenticationPrincipal PrincipalDetails user) {
      helpfulService.insert(helpfulRequest, user.getUser());

      return ResponseEntity.ok().body(200);
    }

    @DeleteMapping("/helpful")
    public ResponseEntity deleteHelpful(@RequestBody HelpfulRequest helpfulRequest, @AuthenticationPrincipal PrincipalDetails user) {
      helpfulService.delete(helpfulRequest, user.getUser());

      return ResponseEntity.ok().body(200);
    }

}
