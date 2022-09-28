package app.learningtrip.apiserver.place.dto.response;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Data
@Getter @Setter @SuperBuilder
public class PlaceResponse {

    protected Long id;

    protected String name;

    protected String description;

    protected String imageURL;

    protected String address;

    protected Double latitude;

    protected Double longitude;

    protected String tel;

    protected String restDate;

    protected String useTime;

    protected String parking;

    protected boolean babyCarriage;

    protected boolean pet;

    protected boolean textbook;
}
