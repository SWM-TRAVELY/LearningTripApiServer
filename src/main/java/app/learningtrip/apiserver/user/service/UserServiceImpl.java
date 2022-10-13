package app.learningtrip.apiserver.user.service;

import app.learningtrip.apiserver.user.domain.User;
import app.learningtrip.apiserver.user.dto.request.SignUpRequest;
import app.learningtrip.apiserver.user.dto.response.StatusResponse;
import app.learningtrip.apiserver.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService{

  private final UserRepository userRepository;

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
}
