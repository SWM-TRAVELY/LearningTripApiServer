package app.learningtrip.apiserver.user.controller;

import app.learningtrip.apiserver.user.dto.ReissueTokenRequest;
import app.learningtrip.apiserver.user.dto.ReissueTokenResponse;
import app.learningtrip.apiserver.user.dto.SignUpRequest;
import app.learningtrip.apiserver.user.dto.StatusResponse;
import app.learningtrip.apiserver.user.service.UserServiceImpl;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class UserController {

  private final UserServiceImpl userService;

  @PostMapping("user/signup")
  public ResponseEntity<StatusResponse> signUp(@Valid @RequestBody SignUpRequest request){
    return ResponseEntity.ok().body(userService.signUp(request));
  }

  @GetMapping("user/check_duplicated")
  public ResponseEntity<StatusResponse> checkUsernameDuplicated(@RequestParam(name = "username") String username){
    return ResponseEntity.ok().body(userService.checkUsernameDuplicated(username));
  }

  @PostMapping("auth/reissue_token")
  public ResponseEntity<ReissueTokenResponse> reissueToken(@RequestBody ReissueTokenRequest request) {
    return ResponseEntity.ok().body(userService.reissueToken(request));
  }

  @GetMapping("/user")
  public ResponseEntity<String> user(){
    return ResponseEntity.ok().body("sieun");
  }

}
