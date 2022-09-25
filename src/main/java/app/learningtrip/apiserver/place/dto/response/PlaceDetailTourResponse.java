package app.learningtrip.apiserver.place.dto.response;

import app.learningtrip.apiserver.place.domain.Place;
import app.learningtrip.apiserver.place.domain.PlaceDetailTour;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Data
@Getter @Setter @SuperBuilder
public class PlaceDetailTourResponse extends PlaceResponse {

    private String experienceAge;

    private String experienceInfo;

    private boolean worldCulturalHeritage;

    private boolean worldNaturalHeritage;

    private boolean worldRecordHeritage;

    public static PlaceDetailTourResponse toResponse(PlaceDetailTour placeDetailTour) {
        return PlaceDetailTourResponse.builder()
            .id(placeDetailTour.getPlace().getId())
            .name(placeDetailTour.getPlace().getName())
            .description(placeDetailTour.getPlace().getDescription())
            .imageURL(placeDetailTour.getPlace().getImageURL1())
            .address(placeDetailTour.getPlace().getAddress())
            .latitude(placeDetailTour.getPlace().getLatitude())
            .longitude(placeDetailTour.getPlace().getLongitude())
            .tel(placeDetailTour.getPlace().getTel())
            .restDate(placeDetailTour.getPlace().getRestDate())
            .useTime(placeDetailTour.getPlace().getUseTime())
            .parking(placeDetailTour.getPlace().getParking())
            .babyCarriage(placeDetailTour.getPlace().isBabyCarriage())
            .pet(placeDetailTour.getPlace().isPet())
            .textbook(placeDetailTour.getPlace().isTextbook())
            .experienceAge(placeDetailTour.getExperienceAge())
            .experienceInfo(placeDetailTour.getExperienceInfo())
            .worldCulturalHeritage(placeDetailTour.isWorldCulturalHeritage())
            .worldNaturalHeritage(placeDetailTour.isWorldNaturalHeritage())
            .worldRecordHeritage(placeDetailTour.isWorldRecordHeritage())
            .build();
    }
}
