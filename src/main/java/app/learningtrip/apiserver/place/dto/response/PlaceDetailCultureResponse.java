package app.learningtrip.apiserver.place.dto.response;

import app.learningtrip.apiserver.place.domain.Place;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter @Setter @SuperBuilder
@Data
public class PlaceDetailCultureResponse extends Place {

  private String discount;

  private String parkingFee;

  private String useFee;

  private String spendTime;
}
