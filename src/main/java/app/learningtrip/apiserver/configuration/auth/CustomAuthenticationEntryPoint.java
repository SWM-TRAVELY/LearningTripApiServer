package app.learningtrip.apiserver.configuration.auth;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

/*
처리해야 할 exception
1. 토큰 없음
2. 시그니쳐 불일치
3. 토큰 만료
4. 토큰 인증 후 권한 거부
 */

@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

  @Override
  public void commence(HttpServletRequest request, HttpServletResponse response,
      AuthenticationException authException) throws IOException, ServletException {
    response.sendError(HttpServletResponse.SC_UNAUTHORIZED, request.getAttribute("exception").toString());
  }
}
