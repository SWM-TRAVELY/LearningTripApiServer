package app.learningtrip.apiserver.heritage.domain;

import app.learningtrip.apiserver.place.domain.Place;
import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity @Table
@AllArgsConstructor @NoArgsConstructor
@Getter @Setter @Builder
public class Heritage {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="heritage_id", unique = true, nullable = false)
    private Long id;

    @Column(length = 50, nullable = false)
    private String name;            // 이름

    @Column(length = 12)
    private String type;            // 문화재 유형

    private String description;     // 설명

    private String imageURL;        // 이미지

    @Column(length = 110, nullable = false)
    private String address;         // 주소

    @Column(length = 20)
    private Double latitude;        // 위도

    @Column(length = 20)
    private Double longitude;       // 경도

    @Column(length = 100)
    private String period;          // 시대

    @Column(length = 15)
    private String category1;       // 카테고리 1

    @Column(length = 15)
    private String category2;       // 카테고리 2

    @Column(length = 15)
    private String category3;       // 카테고리 3

    @Column(length = 15)
    private String category4;       // 카테고리 4

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id")
    public Place place;
}
