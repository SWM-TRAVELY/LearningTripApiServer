package app.learningtrip.apiserver.user.domain;

import app.learningtrip.apiserver.user.dto.response.UserInfoResponse;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Data
@Entity
public class User {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @Column(unique = true)
  private String username;

  private String password;

  private String role;

  private String phone;

  private String image;

  private String email;

  private String nickname;

  @Column(name = "login_provider")
  private String loginProvider;

  @Column(name = "signup_status")
  private Boolean signUpStatus;

  public User(String username, String password, String role, String phone, String image,
      String email, String nickname, String loginProvider, Boolean signUpStatus) {
    this.username = username;
    this.password = password;
    this.role = role;
    this.phone = phone;
    this.image = image;
    this.email = email;
    this.nickname = nickname;
    this.loginProvider = loginProvider;
    this.signUpStatus = signUpStatus;
  }

  @Column(nullable = true)
  private String refreshToken;

  public User(String username, String image, String email, String nickname, String loginProvider) {
    this.username = username;
    this.image = image;
    this.email = email;
    this.nickname = nickname;
    this.loginProvider = loginProvider;
  }

  public UserInfoResponse toUserInfo(){
    return new UserInfoResponse(this.email, this.nickname, this.image, this.phone, this.loginProvider);
  }

  public List<String> roles() {
    if(role.length() > 0){
      return Arrays.asList(role.split(","));
    }
    return new ArrayList<>();
  }
}
