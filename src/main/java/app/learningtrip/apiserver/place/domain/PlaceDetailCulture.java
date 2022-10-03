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

@Entity @Table(name = "Place_Detail_Culture")
@AllArgsConstructor @NoArgsConstructor
@Getter @Setter @Builder
public class PlaceDetailCulture implements Serializable {

    @Id
    private Long id;

    @Column(length = 600)
    private String discount;        // 할인정보

    @Column(length = 600)
    private String parkingFee;      // 주차요금

    @Column(length = 1600)
    private String useFee;          // 이용요금

    @Column(length = 200)
    private String spendTime;       // 소요시간

    @OneToOne
    @MapsId
    private Place place;
}
