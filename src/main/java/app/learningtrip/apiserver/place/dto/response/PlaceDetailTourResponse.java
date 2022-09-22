package app.learningtrip.apiserver.place.dto.response;

import app.learningtrip.apiserver.place.domain.Place;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter @Setter
@SuperBuilder
@Data
public class PlaceDetailTourResponse extends Place {

    private String experienceAge;

    private String experienceInfo;

    private boolean heritageCulture;

    private boolean heritageNatural;

    private boolean heritageRecord;
}
