package app.learningtrip.apiserver.user.service;

import app.learningtrip.apiserver.common.dto.ResponseTemplate;
import app.learningtrip.apiserver.user.dto.response.StatusResponse;
import app.learningtrip.apiserver.user.dto.request.SignUpRequest;
import app.learningtrip.apiserver.user.dto.response.TokenResponse;

public interface UserService {
  ResponseTemplate<TokenResponse> signUp(SignUpRequest request);

  ResponseTemplate<Object> checkUsernameDuplicated(String username);
}
