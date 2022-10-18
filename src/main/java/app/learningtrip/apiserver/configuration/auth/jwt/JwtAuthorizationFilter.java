package app.learningtrip.apiserver.configuration.auth.jwt;

import app.learningtrip.apiserver.configuration.auth.PrincipalDetails;
import app.learningtrip.apiserver.user.domain.User;
import app.learningtrip.apiserver.user.repository.UserRepository;
import com.auth0.jwt.exceptions.InvalidClaimException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

  private final UserRepository userRepository;

  private final JwtService jwtService;

  public JwtAuthorizationFilter(AuthenticationManager authenticationManager,
      UserRepository userRepository, JwtService jwtService) {
    super(authenticationManager);
    this.userRepository = userRepository;
    this.jwtService = jwtService;
  }

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
      FilterChain chain) throws IOException, ServletException {

//    Authorization 헤더에서 토큰 가져오기
    String token = request.getHeader("Authorization");

    if (token == null || !token.startsWith(JwtProperties.TOKEN_PREFIX)) {
//      xh
      request.setAttribute("exception","NoTokenException");
    } else {

      try {
//        JWT 토큰 유효성 검증과 동시에 username을 복호화 한다
        String username = jwtService.checkJwtValidation(token);

        if (username != null) {
//        토큰에서 복호화한 username으로 DB에서 User 조회해서 PrincipalDetails 객체 생성
          User user = userRepository.findByUsername(username)
              .orElseThrow(() -> new InvalidClaimException(null));
          PrincipalDetails principalDetails = new PrincipalDetails(user);

//        PrincipalDetails 객체와 권한으로 인증 객체 생성
//        +) 위에서 토큰인증 했으므로 비밀번호 받아서 인증할 필요 없고 권한 처리 목적임
          Authentication authentication = new UsernamePasswordAuthenticationToken(principalDetails,
              null, principalDetails.getAuthorities());

//        시큐리티 세션에 접근하여 강제로 인증 객체 저장
          SecurityContextHolder.getContext().setAuthentication(authentication);

//        chain.doFilter(request, response);
        }
      } catch (SignatureVerificationException | TokenExpiredException | InvalidClaimException e) {
        request.setAttribute("exception", e.getClass().getSimpleName());
      }
    }
    super.doFilterInternal(request, response, chain);
  }
}
