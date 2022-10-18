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
  private Long id;

  @Column(unique = true)
  private String username;

  private String password;

  private String role;

  private String phone;

  private String image;

  private String email;

  private String nickname;

  private String level;

  private Integer experiencePoint;

  @Column(name = "login_provider")
  private String loginProvider;

  @Column(name = "signup_status")
  private Boolean signUpStatus;

  public User(String username, String password, String role, String phone, String image,
      String email, String nickname, String loginProvider, Boolean signUpStatus, String level, int exp) {
    this.username = username;
    this.password = password;
    this.role = role;
    this.phone = phone;
    this.image = image;
    this.email = email;
    this.nickname = nickname;
    this.loginProvider = loginProvider;
    this.signUpStatus = signUpStatus;
    this.level = level;
    this.experiencePoint = exp;
  }

  private String refreshToken;

  public User(String username, String image, String email, String nickname, String loginProvider) {
    this.username = username;
    this.image = image;
    this.email = email;
    this.nickname = nickname;
    this.loginProvider = loginProvider;
  }

  public UserInfoResponse toUserInfo(){
    return new UserInfoResponse(this.email, this.nickname, this.image, this.phone,
        this.loginProvider, this.level, this.experiencePoint);
  }

  public List<String> roles() {
    if(role.length() > 0){
      return Arrays.asList(role.split(","));
    }
    return new ArrayList<>();
  }

  /**
   * 경험치 상승에 따라 자동으로 레벨 업
   * @param point 경험치
   */
  public void upExp(int point){
    this.experiencePoint += point;
    this.level = getLevelByExp(this.experiencePoint);
  }

  /**
   * 경험치로 레벨 계산
   * @param exp 경험치
   * @return 경험치에 맞는 레벨
   */
  private String getLevelByExp(int exp){
    if(exp == 0) {
      return "초보 탐험가";
    }
    else if(exp > 0 && exp < 300) {
      return "견습 탐험가";
    }
    else if(exp >= 300 && exp < 700) {
      return "숙련 탐험가";
    }
    else if(exp >= 700 && exp < 1500) {
      return "전문 탐험가";
    }
    else if(exp >= 1500 && exp < 3100) {
      return "장인 탐험가";
    }
    else if(exp >= 3100) {
      return "명인 탐험가";
    }
    return null;
  }
}
