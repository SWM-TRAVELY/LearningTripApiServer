package app.learningtrip.apiserver.user.controller;

import app.learningtrip.apiserver.common.dto.ResponseTemplate;
import app.learningtrip.apiserver.configuration.auth.PrincipalDetails;
import app.learningtrip.apiserver.user.dto.request.ResetPasswordRequest;
import app.learningtrip.apiserver.user.dto.request.SignUpRequest;
import app.learningtrip.apiserver.user.dto.request.UpdateUserInfoRequest;
import app.learningtrip.apiserver.user.dto.response.TokenResponse;
import app.learningtrip.apiserver.user.dto.response.UserInfoResponse;
import app.learningtrip.apiserver.user.service.UserServiceImpl;
import io.swagger.annotations.ApiOperation;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("user")
@RestController
public class UserController {

  private final UserServiceImpl userService;

  @ApiOperation(value = "회원가입", notes = """
      회원 가입 성공시 바로 로그인 가능할 수 있도록 토큰 반환
      값 검증은 따로 코드 안 만들었어요 프론트에서 검증해주셔야 함!!!
      username 중복이면 701 코드 반환""")
  @PostMapping("signup")
  public ResponseEntity<ResponseTemplate<TokenResponse>> signUp(@Valid @RequestBody SignUpRequest request){
    return ResponseEntity.ok().body(userService.signUp(request));
  }

  @ApiOperation(value = "이메일 중복 확인", notes = "username 받아서 중복이면 701 코드 반환")
  @GetMapping("check_duplicated")
  public ResponseEntity<ResponseTemplate<Object>> checkUsernameDuplicated(@RequestParam(name = "username") String username){
    return ResponseEntity.ok().body(userService.checkUsernameDuplicated(username));
  }

  @ApiOperation(value = "사용자 정보 조회", notes = "토큰 필수, email, phone은 null값도 있음.")
  @GetMapping("info")
  public ResponseEntity<UserInfoResponse> getUserInfo(@AuthenticationPrincipal PrincipalDetails user){
    return ResponseEntity.ok().body(userService.getUserInfo(user));
  }

  @ApiOperation(value = "사용자 정보 수정", notes = """
      토큰 필수
      keys: 바꿀 항목 배열, values 바꿀 내용 배열
      ex) "keys"=["nickname","phone"], "values"=["바꿀 닉네임","폰번호"]""")
  @PatchMapping ("info")
  public ResponseEntity<UserInfoResponse> updateUserInfo(@RequestBody UpdateUserInfoRequest request,
      @AuthenticationPrincipal PrincipalDetails user) {
    return ResponseEntity.ok().body(userService.updateUserInfo(request, user));
  }

  @ApiOperation(value = "비밀번호 변경", notes = "토큰 필수, 성공시 status 200, 실패시 702")
  @PatchMapping("reset_password")
  public ResponseEntity<Object> resetPassword(@RequestBody ResetPasswordRequest request, @AuthenticationPrincipal PrincipalDetails user){
    return ResponseEntity.ok().body(userService.resetPassword(request, user));
  }

//  단순 DB에서 아예 유저 정보를 삭제할지, 아니면 비활성화 시킬지?
  @ApiOperation(value = "회원 탈퇴", notes = "토큰 필수")
  @DeleteMapping("delete")
  public ResponseEntity<Object> deleteAccount(@AuthenticationPrincipal PrincipalDetails user){
    return ResponseEntity.ok().body(userService.deleteAccount(user));
  }
}
