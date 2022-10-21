package app.learningtrip.apiserver.user.service;

import app.learningtrip.apiserver.common.docs.StatusCode;
import app.learningtrip.apiserver.common.dto.ResponseTemplate;
import app.learningtrip.apiserver.configuration.auth.jwt.JwtProperties;
import app.learningtrip.apiserver.configuration.auth.jwt.JwtService;
import app.learningtrip.apiserver.user.domain.User;
import app.learningtrip.apiserver.user.dto.request.RefreshTokenRequest;
import app.learningtrip.apiserver.user.dto.request.TokenGen4TestRequest;
import app.learningtrip.apiserver.user.dto.response.ReissueTokenResponse;
import app.learningtrip.apiserver.user.dto.response.TokenResponse;
import app.learningtrip.apiserver.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AuthService {

  private final JwtService jwtService;

  private final UserRepository userRepository;

  public ResponseTemplate<ReissueTokenResponse> reissueToken(RefreshTokenRequest request) {
    String refreshToken = request.getRefresh_token();
    String username = jwtService.checkJwtValidation(refreshToken);

    if (!userRepository.findByUsername(username)
        .orElseThrow(null).getRefreshToken().equals(refreshToken)) {
//      RefreshToken 위조 익셉션으로 교체할것
      throw new RuntimeException();
    } else {
      return new ResponseTemplate<>(StatusCode.OK, "ReissueRefreshTokenSuccess",
          new ReissueTokenResponse(jwtService.createJwt("access_token", username)));
    }
  }

  /**
   * API 테스트용 토큰 발급
   * @param request secret, 테스트 할 username
   * @return 액세스 토큰
   */
  public String tokenGen4Test(TokenGen4TestRequest request) {
    if(!request.getSecret().equals(JwtProperties.TEST_SECRET)){
      return "WRONG SECRET!!!";
    }

    return JwtProperties.TOKEN_PREFIX + jwtService.createJwt("access_token", request.getUsername());
  }

  public ResponseTemplate<TokenResponse> autoLogin(RefreshTokenRequest request) {
    String username = jwtService.checkJwtValidation(request.getRefresh_token());

    User user = userRepository.findByUsername(username).orElseThrow(() -> {throw new RuntimeException();});

    if(user.getRefreshToken().equals(request.getRefresh_token())){
      return new ResponseTemplate<>(StatusCode.OK, "AutoLoginSuccess", new TokenResponse(
          jwtService.createJwt("access_token",user.getUsername()),
          jwtService.createJwt("refresh_token", user.getUsername())
      ));
    }
    else {
      throw new RuntimeException();
    }


  }
}
