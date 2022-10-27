package app.learningtrip.apiserver.common.docs;

public interface StatusCode {
  int OK = 200;

//  600번대: auth 검증 관련

//  700번대: user 관련
  int EMAIL_DUPLICATED = 701;
  int CURRENT_PW_WRONG = 702;

}
