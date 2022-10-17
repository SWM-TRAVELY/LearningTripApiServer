package app.learningtrip.apiserver.configuration.auth.jwt;

import app.learningtrip.apiserver.common.dto.ResponseTemplate;
import app.learningtrip.apiserver.user.dto.response.TokenResponse;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.google.gson.Gson;
import java.util.Date;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class JwtService {

  private final Gson gson;

  /**
   * JWT 생성
   * @param tokenType 토큰 종류 (access_token or refresh_token)
   * @param username username
   * @return JWT
   */
  public String createJwt(String tokenType, String username) {
    long expirationTime;
    if(tokenType.equals("access_token")) {
      expirationTime = JwtProperties.ACCESS_TOKEN_EXPIRATION_TIME;
    } else if(tokenType.equals("refresh_token")) {
      expirationTime = JwtProperties.REFRESH_TOKEN_EXPIRATION_TIME;
    } else {
      throw new RuntimeException("tokenNameInvalidException");
    }

    return JWT.create()
        .withSubject(tokenType)
        .withExpiresAt(new Date(System.currentTimeMillis()+expirationTime))
        .withClaim("username", username)
        .sign(Algorithm.HMAC512(JwtProperties.SECRET));
  }

  /**
   * JWT 토큰 유효성 검증과 동시에 username을 복호화
   * @param token JWT(String, "Bearer {TOKEN}")
   * @return username (JWT가 담고 있는 body)
   */
  public String checkJwtValidation(String token) {
    return JWT.require(Algorithm.HMAC512(JwtProperties.SECRET)).build()
        .verify(token.replace(JwtProperties.TOKEN_PREFIX, ""))
        .getClaim("username").asString();
  }

  /**
   * accessToken과 refreshToken을 담은 json body 문자열 생성
   * @param accessToken accessToken
   * @param refreshToken refreshToken
   * @return accessToken + refreshToken response body
   */

  public String createBodyWithTokens(String accessToken, String refreshToken) {
    return gson.toJson(new ResponseTemplate<>(200, "LoginSuccess",
        new TokenResponse(JwtProperties.TOKEN_PREFIX + accessToken,
        JwtProperties.TOKEN_PREFIX + refreshToken)));
  }
}
