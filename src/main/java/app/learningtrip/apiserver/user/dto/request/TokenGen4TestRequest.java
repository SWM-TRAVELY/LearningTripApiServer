package app.learningtrip.apiserver.user.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class TokenGen4TestRequest {
  @ApiModelProperty(value = "Secret Key")
  private String secret;

  @ApiModelProperty(value = "username, 이메일 또는 소셜로그인 고유 ID", example = "test@test.app")
  private String username;
}
