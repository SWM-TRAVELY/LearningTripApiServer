package app.learningtrip.apiserver.user.dto.request;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ResetPasswordRequest {
  private String current_password;
  private String new_password;
}
