package app.learningtrip.apiserver.course.dto.response;

import app.learningtrip.apiserver.place.domain.Place;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter @Setter @Builder
public class CoursePlaceResponse {

    Long id;

    String name;

    String description;

    String imageURL;

    String address;

    public static CoursePlaceResponse toResponse(Place place) {
        return CoursePlaceResponse.builder()
            .id(place.getId())
            .name(place.getName())
            .description(place.getDescription())
            .imageURL(place.getImageURL1())
            .address(place.getAddress())
            .build();
    }
}
