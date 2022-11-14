package app.learningtrip.apiserver.course.domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter @Setter
@Entity
public class Province {

  @Id
  private Long id;

  private String province;

  private String city;
}
