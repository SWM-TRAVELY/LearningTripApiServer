package app.learningtrip.apiserver.user.service;

import app.learningtrip.apiserver.user.dto.ReissueTokenRequest;
import app.learningtrip.apiserver.user.dto.ReissueTokenResponse;
import app.learningtrip.apiserver.user.dto.StatusResponse;
import app.learningtrip.apiserver.user.dto.SignUpRequest;

public interface UserService {
  StatusResponse signUp(SignUpRequest request);

  StatusResponse checkUsernameDuplicated(String username);

  ReissueTokenResponse reissueToken(ReissueTokenRequest request);
}
