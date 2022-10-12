package app.learningtrip.apiserver.user.dto;

import app.learningtrip.apiserver.user.domain.User;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class SignUpRequest {

  private String username;

  private String password;

  private String phone;

  private String nickname;

   public User toEntity(String email, String role, String image, String loginProvider, Boolean signUpStatus){
     return new User(this.username, this.password, role, this.phone, image, email,
         this.nickname, loginProvider, signUpStatus);
   }
}
