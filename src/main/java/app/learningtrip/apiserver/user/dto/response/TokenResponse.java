package app.learningtrip.apiserver.user.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class TokenResponse {
  private String access_token;
  private String refresh_token;
}