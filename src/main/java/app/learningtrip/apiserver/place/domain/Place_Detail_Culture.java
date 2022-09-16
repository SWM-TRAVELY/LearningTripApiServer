package app.learningtrip.apiserver.place.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter @Setter
@SuperBuilder
public class Place_Detail_Culture extends Place {

  private String discount;
  private String parkingFee;

  private String useFee;
  private String spendTime;
}
