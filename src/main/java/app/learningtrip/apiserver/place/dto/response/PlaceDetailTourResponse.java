package app.learningtrip.apiserver.place.dto.response;

import app.learningtrip.apiserver.place.domain.Place;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Data
@Getter @Setter @SuperBuilder
public class PlaceDetailTourResponse extends PlaceResponse {

    private String experienceAge;

    private String experienceInfo;

    private boolean heritageCulture;

    private boolean heritageNatural;

    private boolean heritageRecord;
}
