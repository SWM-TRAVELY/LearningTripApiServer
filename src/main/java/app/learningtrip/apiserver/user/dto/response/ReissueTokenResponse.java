package app.learningtrip.apiserver.user.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ReissueTokenResponse {
  private String access_token;
}
