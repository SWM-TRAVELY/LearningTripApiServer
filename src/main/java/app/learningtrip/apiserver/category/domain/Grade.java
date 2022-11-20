package app.learningtrip.apiserver.category.domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter @Getter
@NoArgsConstructor
@Entity
public class Grade {

  @Id
  private Long id;

  private String school;

  private String grade;
}
