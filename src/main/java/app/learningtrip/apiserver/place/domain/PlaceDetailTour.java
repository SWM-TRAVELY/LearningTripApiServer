package app.learningtrip.apiserver.place.domain;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;


@Entity @Table(name = "Place_Detail_Tour")
@AllArgsConstructor @NoArgsConstructor
@Getter @Setter @Builder
public class PlaceDetailTour implements Serializable {

    @Id
    private Long id;

    @Column(length = 200)
    private String experienceAge;       // 체험가능연령

    @Column(length = 6700)
    private String experienceInfo;      // 체험안내

    private boolean worldCulturalHeritage;    // 세계문화유산 유무

    private boolean worldNaturalHeritage;    // 세계자연유산 유무

    private boolean worldRecordHeritage;     // 세계기록유산 유무

    @OneToOne
    @MapsId
    private Place place;
}
