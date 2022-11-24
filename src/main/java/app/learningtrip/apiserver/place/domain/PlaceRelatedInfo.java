package app.learningtrip.apiserver.place.domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
@Entity
public class PlaceRelatedInfo {

  @Id
  private Long id;

  private String name;

  private Integer openingDays;

  private Integer grades;

  private String province;

  private String city;
}
