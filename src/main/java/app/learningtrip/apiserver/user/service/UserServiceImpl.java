package app.learningtrip.apiserver.user.service;

import app.learningtrip.apiserver.common.docs.StatusCode;
import app.learningtrip.apiserver.common.dto.ResponseTemplate;
import app.learningtrip.apiserver.configuration.auth.PrincipalDetails;
import app.learningtrip.apiserver.configuration.auth.jwt.JwtService;
import app.learningtrip.apiserver.level.repository.LevelRepository;
import app.learningtrip.apiserver.user.domain.User;
import app.learningtrip.apiserver.user.dto.request.ResetPasswordRequest;
import app.learningtrip.apiserver.user.dto.request.SignUpRequest;
import app.learningtrip.apiserver.user.dto.request.UpdateUserInfoRequest;
import app.learningtrip.apiserver.user.dto.response.TokenResponse;
import app.learningtrip.apiserver.user.dto.response.UserInfoResponse;
import app.learningtrip.apiserver.user.repository.UserRepository;
import java.util.List;
import java.util.NoSuchElementException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService{

  private final UserRepository userRepository;

  private final LevelRepository levelRepository;

  private final BCryptPasswordEncoder bCryptPasswordEncoder;

  private final JwtService jwtService;

  /**
   * 회원가입
   * @param request 회원가입에 필요한 정보
   * @return 회원가입에 대한 StatusResponse
   */
  @Override
  public ResponseTemplate<TokenResponse> signUp(SignUpRequest request) {

    if(checkUsernameDuplicated(request.getUsername()).getStatus() == StatusCode.EMAIL_DUPLICATED){
      return new ResponseTemplate<>(StatusCode.EMAIL_DUPLICATED, "UsernameAlreadyExist", null);
    }

    request.setPassword(bCryptPasswordEncoder.encode(request.getPassword()));

    User user = request.toEntity(request.getUsername(),
        "ROLE_USER","https://image.learningtrip.app/profile/default.jpg");

    userRepository.save(user);

    TokenResponse token = new TokenResponse(jwtService.createJwt("access_token", user.getUsername()),
        jwtService.createJwt("refresh_token", user.getUsername()));


    return new ResponseTemplate<>(StatusCode.OK, "SignUpSuccess", token);
  }

  /**
   * Username 중복 검사
   *
   * @param username username
   * @return 중복 검사에 대한 StatusResponse
   */
  @Override
  public ResponseTemplate<Object> checkUsernameDuplicated(String username) {
    if(userRepository.findByUsername(username).isPresent()){
      return new ResponseTemplate<>(StatusCode.EMAIL_DUPLICATED, "UsernameAlreadyExist", null);
    }
    return new ResponseTemplate<>(StatusCode.OK, "UsernameNotExist", null);
  }

  /**
   * 유저 정보 조회
   * @param authUser PrincipalDetails 객체
   * @return 유저 정보
   */
  public UserInfoResponse getUserInfo(PrincipalDetails authUser){
    User user = userRepository.findByUsername(authUser.getUser().getUsername()).orElseThrow(() -> {
//      400 or 401 에러로 교체 할 것
      throw new RuntimeException();
    });

    return user.toUserInfo(user.getLevel(levelRepository));
  }

  /**
   * 유저 정보 수정
   * @param request keys (바꿀 항목) 배열, values (바꿀 내용) 배열
   * @param authUser PrincipalDetails 객체
   * @return 수정한 유저 정보
   */
  public UserInfoResponse updateUserInfo(UpdateUserInfoRequest request, PrincipalDetails authUser){
    User user = userRepository.findByUsername(authUser.getUsername()).orElseThrow(() -> {
//      Todo: 400 or 401로 교체 할 것
      throw new RuntimeException();
    });

    List<String> keys = request.getKeys();
    List<String> values = request.getValues();

    if(keys.size() != values.size()){
//      Todo: 400 bad request로 교체 할 것
      throw new RuntimeException();
    }

    for(int i = 0; i<keys.size(); i++){
      switch (keys.get(i)) {
        case "nickname" -> user.setNickname(values.get(i));
        case "image" -> user.setImage(values.get(i));
        case "phone" -> user.setPhone(values.get(i));
        default ->
//        Todo: 400 bad request로 교체하던지 requestDto에서 값 검증 할것
            throw new RuntimeException();
      }
    }

    userRepository.save(user);

    return userRepository.findByUsername(user.getUsername()).orElseThrow(() -> {
      throw new RuntimeException();
    }).toUserInfo(user.getLevel(levelRepository));
  }

  /**
   * 비밀번호 재설정
   * @param request 현재/신규 비밀번호
   * @param authUser 인가 사용자 정보
   * @return 성공시 200, 실패시 702 반환
   */
  public ResponseTemplate<Object> resetPassword(ResetPasswordRequest request, PrincipalDetails authUser) {
    User user = userRepository.findByUsername(authUser.getUsername()).orElseThrow(() -> {
      throw new NoSuchElementException();
    });

    ResponseTemplate<Object> response = new ResponseTemplate<>();
    response.setData(null);

    if(bCryptPasswordEncoder.matches(request.getCurrent_password(), user.getPassword())){
      user.setPassword(bCryptPasswordEncoder.encode(request.getNew_password()));
      userRepository.save(user);

      response.setStatus(200);
      response.setMessage("ResetPasswordSuccess");
    }
    else{
      response.setStatus(StatusCode.CURRENT_PW_WRONG);
      response.setMessage("WrongCurrentPassword");
    }

    return response;
  }


  /**
   * DB에서 user 삭제
   * @param authUser 인가 사용자 정보
   * @return 성공시 200 반환
   */
  public ResponseTemplate<Object> deleteAccount(PrincipalDetails authUser){
    User user = userRepository.findByUsername(authUser.getUsername()).orElseThrow(() -> {
      throw new NoSuchElementException();
    });

    userRepository.delete(user);

    return new ResponseTemplate<>(200, "AccountDeleted", null);
  }
}
