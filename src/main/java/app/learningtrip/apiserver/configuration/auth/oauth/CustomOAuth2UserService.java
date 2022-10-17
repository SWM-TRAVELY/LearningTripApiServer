package app.learningtrip.apiserver.configuration.auth.oauth;

import app.learningtrip.apiserver.user.domain.User;
import app.learningtrip.apiserver.user.repository.UserRepository;
import java.util.Collections;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

  private final UserRepository userRepository;

  @Override
  public OAuth2User loadUser(OAuth2UserRequest oAuth2UserRequest) throws OAuth2AuthenticationException {
    OAuth2UserService<OAuth2UserRequest, OAuth2User> oAuth2UserService = new DefaultOAuth2UserService();

    OAuth2User oAuth2User = oAuth2UserService.loadUser(oAuth2UserRequest);

//    request에서 resgistrationId를 문자열로 받아온다.
    String registrationId = oAuth2UserRequest.getClientRegistration().getRegistrationId();

    OAuth2Attributes attributes = OAuth2Attributes.of(registrationId, oAuth2User.getAttributes());

    if(userRepository.findByUsername(attributes.getUsername()).orElse(null) == null) {
      User user = attributes.toEntity();

      user.setRole("ROLE_USER");
      user.setSignUpStatus(true);

      userRepository.save(user);
    }

    return new DefaultOAuth2User(Collections.singleton(new SimpleGrantedAuthority("ROLE_USER")),
        attributes.getAttributes(), attributes.getAttributeKey());

  }
}
