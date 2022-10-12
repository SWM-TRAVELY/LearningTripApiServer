package app.learningtrip.apiserver.configuration.auth.jwt;

import app.learningtrip.apiserver.configuration.auth.PrincipalDetails;
import app.learningtrip.apiserver.user.domain.User;
import app.learningtrip.apiserver.user.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
  private final AuthenticationManager authenticationManager;
  private final UserRepository userRepository;

  private final JwtService jwtService;

  private User requestUser = null;

  @Override
  public Authentication attemptAuthentication
      (HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

    try {
//      Request Body에서 아이디/비밀번호 가져와서 유저 객체에 넣어준다
      ObjectMapper om = new ObjectMapper();
      requestUser = om.readValue(request.getInputStream(), User.class);

//      위 유저 객체로 인증 토큰 생성
      UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(requestUser.getUsername(), requestUser.getPassword());

//      authenticate 함수가 호출되면
//      1. AuthenticationProvider가 PrincipalDetailsService의 loadUserByUsername를 호출해서 UserDetails를 리턴 받는다.
//      2. UserDetails(DB)의 Password와 Credential를 비교해서 비밀번호 비교
//      3-1. 성공시 successfulAuthentication 호출
//      3-2. 실패시 unsuccessfulAuthentication 호출
//      4. authentication 객체 생성해서 필터 체인으로 리턴
      return authenticationManager.authenticate(authenticationToken);

    } catch (IOException e) {
      e.printStackTrace();
    }

    return null;
  }

  @Override
  protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
      FilterChain chain, Authentication authResult) throws IOException, ServletException {

//    인증 결과에서 유저 정보 가져오기
    PrincipalDetails principalDetails = (PrincipalDetails) authResult.getPrincipal();

//    AccessToken, RefreshToken 생성
    String accessToken = jwtService.createJwt("access_token", principalDetails.getUsername());
    String refreshToken = jwtService.createJwt("refresh_token", principalDetails.getUsername());

//    RefreshToken DB 저장 -> 추후 redis 대체
    principalDetails.getUser().setRefreshToken(refreshToken);
    userRepository.save(principalDetails.getUser());

//    Response 객체 헤더/바디 설정
    response.setContentType("application/json");
    response.setCharacterEncoding("UTF-8");
    response.getWriter()
        .write(jwtService.createBodyWithTokens(accessToken,refreshToken));

  }

  @Override
  protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
      AuthenticationException failed) throws IOException, ServletException {

    User user = userRepository.findByUsername(requestUser.getUsername()).orElse(null);

//    아이디로 유저 조회가 안될 경우
    if(user == null) {
      response.setContentType("application/json");
      response.setCharacterEncoding("UTF-8");
      response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "UsernamePasswordFailureException");

      return;
    }

//    비밀번호가 틀렸을 경우
    response.setContentType("application/json");
    response.setCharacterEncoding("UTF-8");
    response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "UsernamePasswordFailureException");
  }

}
