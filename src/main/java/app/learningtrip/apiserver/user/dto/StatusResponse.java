package app.learningtrip.apiserver.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class StatusResponse {

  private Boolean status;

  private String status_message;

}
