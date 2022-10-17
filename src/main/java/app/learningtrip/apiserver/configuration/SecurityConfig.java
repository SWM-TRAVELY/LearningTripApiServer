package app.learningtrip.apiserver.configuration;

import app.learningtrip.apiserver.configuration.auth.jwt.JwtAuthenticationFilter;
import app.learningtrip.apiserver.configuration.auth.jwt.JwtAuthorizationFilter;
import app.learningtrip.apiserver.configuration.auth.jwt.JwtService;
import app.learningtrip.apiserver.configuration.auth.oauth.CustomAuthenticationFailureHandler;
import app.learningtrip.apiserver.configuration.auth.oauth.CustomAuthenticationSuccessHandler;
import app.learningtrip.apiserver.configuration.auth.oauth.CustomOAuth2UserService;
import app.learningtrip.apiserver.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.filter.CorsFilter;

@RequiredArgsConstructor
@EnableWebSecurity
@Configuration
public class SecurityConfig {

  private final CorsFilter corsFilter;
  private final UserRepository userRepository;

  private final JwtService jwtService;

  private final CustomOAuth2UserService customOAuth2UserService;

  private final CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;

  private final CustomAuthenticationFailureHandler customAuthenticationFailureHandler;

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http
        .csrf().disable() // 쿠키 or 세션을 사용하지 않으므로 disable
        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS); // 세션을 stateless

    http
        .formLogin().disable() // formLogin 안써요
        .httpBasic().disable(); // httpBasic: id/pw를 base64로 인코딩해서 authorization 헤더에 넣어서 http 통신

    http.apply(new CustomDsl()); //커스텀한 필터(corsFilter, 인증/인가 필터) 등록

    http
        .oauth2Login().userInfoEndpoint().userService(customOAuth2UserService)
        .and()
        .successHandler(customAuthenticationSuccessHandler)
        .failureHandler(customAuthenticationFailureHandler).permitAll();

    // URL과 ROLE에 따른 API 접근 권한 부여
    http.authorizeRequests()
        .antMatchers("/user/check_duplicated").permitAll()
        .antMatchers("/user/signup").permitAll()
        .antMatchers("/user/**").hasRole("USER")
        .antMatchers("/course/list").hasRole("USER")
        .antMatchers("/review").hasRole("USER")
        .antMatchers("/helpful").hasRole("USER")
        .anyRequest().permitAll();

    return http.build();
  }

  public class CustomDsl extends AbstractHttpConfigurer<CustomDsl, HttpSecurity> {


    @Override
    public void configure(HttpSecurity http) throws Exception {
      AuthenticationManager authenticationManager = http.getSharedObject(AuthenticationManager.class);

      http
          .addFilter(corsFilter)
          .addFilter(new JwtAuthenticationFilter(authenticationManager, userRepository, jwtService))
          .addFilter(new JwtAuthorizationFilter(authenticationManager, userRepository, jwtService));
    }
  }

  @Bean
  public BCryptPasswordEncoder encodePassword(){
    return new BCryptPasswordEncoder();
  }
}
