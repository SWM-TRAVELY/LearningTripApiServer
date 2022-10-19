package app.learningtrip.apiserver.user.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class UserInfoResponse {
  private String email;
  private String nickname;
  private String image;
  private String phone;
  private String loginProvider;
  private int exp;
  private String level;
}
