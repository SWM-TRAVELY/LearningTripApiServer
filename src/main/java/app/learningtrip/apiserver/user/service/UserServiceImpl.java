package app.learningtrip.apiserver.user.service;

import app.learningtrip.apiserver.configuration.auth.jwt.JwtService;
import app.learningtrip.apiserver.user.domain.User;
import app.learningtrip.apiserver.user.dto.ReissueTokenRequest;
import app.learningtrip.apiserver.user.dto.ReissueTokenResponse;
import app.learningtrip.apiserver.user.dto.SignUpRequest;
import app.learningtrip.apiserver.user.dto.StatusResponse;
import app.learningtrip.apiserver.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService{

  private final UserRepository userRepository;

  private final JwtService jwtService;

  private final BCryptPasswordEncoder bCryptPasswordEncoder;

  /**
   * 회원가입
   * @param request 회원가입에 필요한 정보
   * @return 회원가입에 대한 StatusResponse
   */
  @Override
  public StatusResponse signUp(SignUpRequest request) {

    if(!checkUsernameDuplicated(request.getUsername()).getStatus()){
      return new StatusResponse(false, "UsernameAlreadyExist");
    }

    request.setPassword(bCryptPasswordEncoder.encode(request.getPassword()));

    User user = request.toEntity(request.getUsername(),
        "ROLE_USER","","LT",true);

    userRepository.save(user);

    return new StatusResponse(true, "SignUpSuccess");
  }

  /**
   * Username 중복 검사
   * @param username username
   * @return 중복 검사에 대한 StatusResponse
   */
  @Override
  public StatusResponse checkUsernameDuplicated(String username) {
    if(userRepository.findByUsername(username).isPresent()){
      return new StatusResponse(false, "UsernameAlreadyExist");
    }
    return new StatusResponse(true, "UsernameNotExist");
  }

  @Override
  public ReissueTokenResponse reissueToken(ReissueTokenRequest request) {
    String refreshToken = request.getRefresh_token();
    String username = jwtService.checkJwtValidation(refreshToken);

    if (!userRepository.findByUsername(username)
        .orElseThrow(null).getRefreshToken().equals(refreshToken)) {
//      RefreshToken 위조 익셉션으로 교체할것
      throw new RuntimeException();
    } else {
      return new ReissueTokenResponse(jwtService.createJwt("access_token", username));
    }
  }
}
