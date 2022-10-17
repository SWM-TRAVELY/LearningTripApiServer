package app.learningtrip.apiserver.user.controller;

import app.learningtrip.apiserver.common.dto.ResponseTemplate;
import app.learningtrip.apiserver.configuration.auth.PrincipalDetails;
import app.learningtrip.apiserver.user.dto.request.SignUpRequest;
import app.learningtrip.apiserver.user.dto.request.UpdateUserInfoRequest;
import app.learningtrip.apiserver.user.dto.response.StatusResponse;
import app.learningtrip.apiserver.user.dto.response.TokenResponse;
import app.learningtrip.apiserver.user.dto.response.UserInfoResponse;
import app.learningtrip.apiserver.user.service.UserServiceImpl;
import javax.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("user")
@RestController
public class UserController {

  private final UserServiceImpl userService;

  @PostMapping("signup")
  public ResponseEntity<ResponseTemplate<TokenResponse>> signUp(@Valid @RequestBody SignUpRequest request){
    return ResponseEntity.ok().body(userService.signUp(request));
  }

  @GetMapping("check_duplicated")
  public ResponseEntity<ResponseTemplate<Object>> checkUsernameDuplicated(@RequestParam(name = "username") String username){
    return ResponseEntity.ok().body(userService.checkUsernameDuplicated(username));
  }

//  @ApiOperation(value = "", authorizations = @Authorization(""))
  @GetMapping("info")
  public ResponseEntity<UserInfoResponse> getUserInfo(@AuthenticationPrincipal PrincipalDetails user){
    return ResponseEntity.ok().body(userService.getUserInfo(user));
  }

  @PatchMapping ("info")
  public ResponseEntity<UserInfoResponse> updateUserInfo(@RequestBody UpdateUserInfoRequest request,
      @AuthenticationPrincipal PrincipalDetails user) {
    return ResponseEntity.ok().body(userService.updateUserInfo(request, user));
  }
}
