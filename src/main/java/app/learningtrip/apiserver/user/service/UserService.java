package app.learningtrip.apiserver.user.service;

import app.learningtrip.apiserver.user.dto.request.ReissueTokenRequest;
import app.learningtrip.apiserver.user.dto.response.ReissueTokenResponse;
import app.learningtrip.apiserver.user.dto.response.StatusResponse;
import app.learningtrip.apiserver.user.dto.request.SignUpRequest;

public interface UserService {
  StatusResponse signUp(SignUpRequest request);

  StatusResponse checkUsernameDuplicated(String username);
}
