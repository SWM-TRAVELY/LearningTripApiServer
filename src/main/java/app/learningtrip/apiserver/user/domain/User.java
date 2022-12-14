package app.learningtrip.apiserver.user.domain;

import app.learningtrip.apiserver.level.repository.LevelRepository;
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
  private Long id;

  @Column(unique = true)
  private String username;

  private String password;

  private String role;

  private String phone;

  private String image;

  private String email;

  private String nickname;

  private Integer experiencePoint;

  @Column(name = "login_provider")
  private String loginProvider;

  @Column(name = "signup_status")
  private Boolean signUpStatus;

  public User(String username, String password, String role, String phone, String image,
      String email, String nickname) {
    this.username = username;
    this.password = password;
    this.role = role;
    this.phone = phone;
    this.image = image;
    this.email = email;
    this.nickname = nickname;
    this.loginProvider = "LT";
    this.signUpStatus = true;
    this.experiencePoint = 0;
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

  public UserInfoResponse toUserInfo(String level){
    return new UserInfoResponse(this.email, this.nickname, this.image, this.phone, this.loginProvider, this.experiencePoint, level);
  }

  public List<String> roles() {
    if(role.length() > 0){
      return Arrays.asList(role.split(","));
    }
    return new ArrayList<>();
  }

  public String getLevel(LevelRepository levelRepository) {
    return levelRepository.findLevelByExp(this.experiencePoint).orElse(null);
  }
}
