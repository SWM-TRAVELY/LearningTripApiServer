package app.learningtrip.apiserver.place.dto.response;

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
}
