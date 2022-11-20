package app.learningtrip.apiserver.configuration.auth.oauth;

import app.learningtrip.apiserver.configuration.auth.jwt.JwtProperties;
import app.learningtrip.apiserver.configuration.auth.jwt.JwtService;
import app.learningtrip.apiserver.user.domain.User;
import app.learningtrip.apiserver.user.repository.UserRepository;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class CustomAuthenticationSuccessHandler extends
    SavedRequestAwareAuthenticationSuccessHandler {

  private final JwtService jwtService;

  private final UserRepository userRepository;

  @Override
  public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
      Authentication authentication) throws ServletException, IOException {

    OAuth2AuthenticationToken oAuth2Token = (OAuth2AuthenticationToken) authentication;

    OAuth2Attributes attributes = OAuth2Attributes.of(oAuth2Token.getAuthorizedClientRegistrationId(),
        oAuth2Token.getPrincipal().getAttributes());

    String username = attributes.getUsername();

    User user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(null));

    String accessToken = jwtService.createJwt("access_token", username);
    String refreshToken = jwtService.createJwt("refresh_token", username);

    user.setImage(attributes.getImage());
    user.setRefreshToken(JwtProperties.TOKEN_PREFIX+refreshToken);

    userRepository.save(user);

//      Response 객체 헤더/바디 설정
    response.setContentType("application/json");
    response.setCharacterEncoding("UTF-8");
    response.getWriter()
        .write(jwtService.createBodyWithTokens(accessToken,refreshToken));
  }
}
