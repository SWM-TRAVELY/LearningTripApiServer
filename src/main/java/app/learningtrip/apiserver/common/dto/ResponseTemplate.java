package app.learningtrip.apiserver.common.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ResponseTemplate<T> {
  private int status;
  private String message;
  private T data;
}
