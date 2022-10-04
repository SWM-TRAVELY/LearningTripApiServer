package app.learningtrip.apiserver.place.domain;

import app.learningtrip.apiserver.place.dto.response.PlaceThumbnail;
import java.io.Serializable;
import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity @Table(name = "place")
@AllArgsConstructor @NoArgsConstructor
@Getter @Setter @Builder
public class Place implements Serializable {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    protected Long id;

    @Column(length = 100, nullable = false)
    protected String name;          // 이름

    @Column(length = 3, nullable = false)
    protected int type;             // 관광지 유형

    @Column(length = 10000)
    protected String description;   // 설명

    @Column(length = 100)
    protected String imageURL1;          // 이미지 1

    @Column(length = 100)
    protected String imageURL2;          // 이미지 2

    @Column(length = 100, nullable = false)
    protected String address;       // 주소

    @Column(length = 20)
    protected Double latitude;      // 위도

    @Column(length = 20)
    protected Double longitude;     // 경도

    @Column(length = 100)
    protected String tel;           // 전화번호

    @Column(length = 200)
    protected String info;          // 안내

    @Column(length = 200)
    protected String restDate;      // 쉬는날

    @Column(length = 700)
    protected String useTime;       // 이용시간

    @Column(length = 300)
    protected String parking;       // 주차정보

    protected boolean babyCarriage;     // 유모차대여 정보

    protected boolean pet;              // 애완동물 가능 여부

    protected boolean textbook;         // 교과서 여부

    @OneToOne(mappedBy = "place")
    private PlaceDetailTour placeDetailTour;

    @OneToOne(mappedBy = "place")
    private PlaceDetailCulture placeDetailCulture;

    public PlaceThumbnail toPlaceThumbnail(){
        return PlaceThumbnail.builder()
            .id(this.id)
            .name(this.name)
            .address(this.address)
            .imageURL(this.imageURL1)
            .build();
    }
}
