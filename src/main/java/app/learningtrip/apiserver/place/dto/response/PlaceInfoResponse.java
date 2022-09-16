package app.learningtrip.apiserver.place.dto.response;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class PlaceInfoResponse {
    private long placeId;

    private int typeId;

    private String placeName;

    private String address;

    private boolean chkInTextbook;

    private float latitude;

    private float longitude;

    private String tel;

    private String overview;

    private String image;

    private String restDate;

    private String useTime;

    private boolean chkParking;

    private boolean chkBabyCarriage;

    private boolean chkPets;

    private String ageAvailable;

    private String expGuide;

    private boolean chkWorldCultural;

    private boolean chkWorldNatural;

    private boolean chkWorldRecord;

    private HeritageList heritageList;
}
