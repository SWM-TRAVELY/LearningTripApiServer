package app.learningtrip.apiserver.configuration.auth.oauth;

import app.learningtrip.apiserver.user.domain.User;
import java.util.Map;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class OAuth2Attributes {
  private Map<String, Object> attributes;

  private String attributeKey;

  private String username;

  private String nickname;

  private String email;

  private String image;

  private String loginProvider;

  public static OAuth2Attributes of(String registrationId, Map<String, Object> attributes) {
    if(registrationId.equals("naver")){
      return ofNaver("id", attributes);
    } else if(registrationId.equals("kakao")) {
      return ofKakao("id", attributes);
    } else {
      throw new RuntimeException();
    }
  }

  public static OAuth2Attributes ofNaver(String attributeKey, Map<String, Object> attributes) {
    Map<String, Object> response;

    if(attributes.get("response") != null){
      response = (Map<String, Object>) attributes.get("response");
    }
    else{
      response = attributes;
    }

    return OAuth2Attributes.builder()
        .username((String) response.get("id"))
        .nickname((String) response.get("name"))
        .image((String) response.get("profile_image"))
        .email((String) response.get("email"))
        .loginProvider("naver")
        .attributes(response)
        .attributeKey(attributeKey)
        .build();
  }

  public static OAuth2Attributes ofKakao(String attributeKey, Map<String, Object> attributes) {

    System.out.println(attributes.toString());

    Map<String, Object> kakaoAccount = (Map<String, Object>) attributes.get("kakao_account");  // 카카오로 받은 데이터에서 계정 정보가 담긴 kakao_account 값을 꺼낸다.
    Map<String, Object> profile = (Map<String, Object>) kakaoAccount.get("profile");   // 마찬가지로 profile(nickname, image_url.. 등) 정보가 담긴 값을 꺼낸다.

    System.out.println(kakaoAccount.toString());
    System.out.println(profile.toString());

    return OAuth2Attributes.builder()
        .username((String) attributes.get("id").toString())
        .nickname((String) profile.get("nickname"))
        .image((String) profile.get("profile_image_url"))
        .email("") // 이메일 검수 요청 필요
        .loginProvider("kakao")
        .attributes(attributes)
        .attributeKey(attributeKey)
        .build();
  }


  public User toEntity() {
    return new User(username, image, email, nickname, loginProvider);
  }
}
