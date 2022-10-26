package app.learningtrip.apiserver.place.dto.response;

import app.learningtrip.apiserver.heritage.dto.response.HeritageThumbnail;
import app.learningtrip.apiserver.place.domain.Place;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@AllArgsConstructor @NoArgsConstructor
@Getter @Setter @Builder
public class PlaceThumbnail {
    private Long id;

    private String name;

    private String address;

    private String imageURL;

    public static PlaceThumbnail toThumbnail(Place place) {
        return PlaceThumbnail.builder()
            .id(place.getId())
            .name(place.getName())
            .address(place.getAddress())
            .imageURL(place.getImageURL1())
            .build();
    }
}
