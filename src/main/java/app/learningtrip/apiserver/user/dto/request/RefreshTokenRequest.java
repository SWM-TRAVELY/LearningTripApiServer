package app.learningtrip.apiserver.user.dto.request;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class RefreshTokenRequest {
  private String refresh_token;
}
