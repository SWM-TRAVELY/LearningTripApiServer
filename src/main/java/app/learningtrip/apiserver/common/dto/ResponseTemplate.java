package app.learningtrip.apiserver.common.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ResponseTemplate<T> {
  private int status;
  private String message;
  private T data;
}
