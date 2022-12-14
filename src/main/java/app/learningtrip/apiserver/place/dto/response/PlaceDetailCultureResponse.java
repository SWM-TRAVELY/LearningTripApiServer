package app.learningtrip.apiserver.place.dto.response;

import app.learningtrip.apiserver.place.domain.Place;
import app.learningtrip.apiserver.place.domain.PlaceDetailCulture;
import app.learningtrip.apiserver.place.domain.PlaceDetailTour;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Data
@Getter @Setter @SuperBuilder
@EqualsAndHashCode(callSuper=false) // 더미데이터 삭제 시 삭제 필요!
public class PlaceDetailCultureResponse extends PlaceResponse {

  private String discount;

  private String parkingFee;

  private String useFee;

  private String spendTime;

  public static PlaceDetailCultureResponse toResponse(PlaceDetailCulture placeDetailCulture) {
    return PlaceDetailCultureResponse.builder()
        .id(placeDetailCulture.getPlace().getId())
        .name(placeDetailCulture.getPlace().getName())
        .description(placeDetailCulture.getPlace().getDescription())
        .imageURL(placeDetailCulture.getPlace().getImageURL1())
        .address(placeDetailCulture.getPlace().getAddress())
        .latitude(placeDetailCulture.getPlace().getLatitude())
        .longitude(placeDetailCulture.getPlace().getLongitude())
        .tel(placeDetailCulture.getPlace().getTel())
        .restDate(placeDetailCulture.getPlace().getRestDate())
        .useTime(placeDetailCulture.getPlace().getUseTime())
        .parking(placeDetailCulture.getPlace().getParking())
        .babyCarriage(placeDetailCulture.getPlace().isBabyCarriage())
        .pet(placeDetailCulture.getPlace().isPet())
        .textbook(placeDetailCulture.getPlace().isTextbook())
        .discount(placeDetailCulture.getDiscount())
        .parkingFee(placeDetailCulture.getParkingFee())
        .useFee(placeDetailCulture.getUseFee())
        .spendTime(placeDetailCulture.getSpendTime())
        .build();
  }
}
