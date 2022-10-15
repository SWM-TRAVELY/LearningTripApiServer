package app.learningtrip.apiserver.user.dto.request;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class ReissueTokenRequest {
  private String refresh_token;
}
