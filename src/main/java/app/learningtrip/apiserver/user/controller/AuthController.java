package app.learningtrip.apiserver.user.controller;

import app.learningtrip.apiserver.common.dto.ResponseTemplate;
import app.learningtrip.apiserver.user.dto.request.RefreshTokenRequest;
import app.learningtrip.apiserver.user.dto.request.TokenGen4TestRequest;
import app.learningtrip.apiserver.user.dto.response.ReissueTokenResponse;
import app.learningtrip.apiserver.user.dto.response.TokenResponse;
import app.learningtrip.apiserver.user.service.AuthService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("auth")
@RestController
public class AuthController {

  private final AuthService authService;

  @ApiOperation(value = "테스트용 토큰 발급", notes = "원하는 유저의 토큰을 발급 (개발 테스트용)")
  @PostMapping("generate_token")
  public ResponseEntity<String> generateToken4Test(@RequestBody TokenGen4TestRequest request) {
    return ResponseEntity.ok().body(authService.tokenGen4Test(request));
  }

  @PostMapping("reissue_token")
  public ResponseEntity<ResponseTemplate<ReissueTokenResponse>> reissueToken(@RequestBody RefreshTokenRequest request) {
    return ResponseEntity.ok().body(authService.reissueToken(request));
  }

  @PostMapping("auto_login")
  public ResponseEntity<ResponseTemplate<TokenResponse>> autoLogin(@RequestBody RefreshTokenRequest request) {
    return ResponseEntity.ok().body(authService.autoLogin(request));
  }
}
