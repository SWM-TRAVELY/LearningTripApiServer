package app.learningtrip.apiserver.user.dto.request;

import java.util.List;
import java.util.Map;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Data
public class UpdateUserInfoRequest {
  private List<String> keys;
  private List<String> values;
}
