package app.learningtrip.apiserver.place.dto.response;

import app.learningtrip.apiserver.place.domain.Place;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Data
@Getter @Setter @SuperBuilder
public class PlaceDetailCultureResponse extends PlaceResponse {

  private String discount;

  private String parkingFee;

  private String useFee;

  private String spendTime;
}
