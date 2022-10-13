package app.learningtrip.apiserver.user.dto;

import app.learningtrip.apiserver.user.domain.User;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.validation.annotation.Validated;

@Validated
@NoArgsConstructor
@Data
public class SignUpRequest {

//  이메일 형식이어여 함
  @Email @NotNull
  private String username;

// 숫자 + (영 대문자 or 영 소문자 or 특수문자) 1자 이상 6~20자
//  특수문자는 ‘!, @, #, $, %, ^, &, *’만 사용 가능
  @Pattern(regexp = "^(?=.*\\d)(?=.*[a-zA-Z#?!@$ %^&*]).{6,20}$")
  private String password;

//  01x(0,1,6,7,8,9)-(3~4자)-4자 (전화번호 형식)
  @Pattern(regexp = "^01(?:0|1|[6-9])-\\d{3,4}-\\d{4}$")
  private String phone;

//  2~12자
  @Length(min = 2, max = 12)
  private String nickname;

   public User toEntity(String email, String role, String image, String loginProvider, Boolean signUpStatus){
     return new User(this.username, this.password, role, this.phone, image, email,
         this.nickname, loginProvider, signUpStatus);
   }
}
