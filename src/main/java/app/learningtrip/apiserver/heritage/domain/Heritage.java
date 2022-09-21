package app.learningtrip.apiserver.heritage.domain;

import app.learningtrip.apiserver.place.domain.Place;
import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Entity
@Getter @Setter
@Builder
@Table
@AllArgsConstructor
@NoArgsConstructor
public class Heritage {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="heritage_id", unique = true, nullable = false)
    private Long id;
    @Column(length = 50, nullable = false)
    private String name;
    @Column(length = 12)
    private String type;
    private String overview;

    @Column(length = 110, nullable = false)
    private String address;
    @Column(length = 20)
    private Double latitude;
    @Column(length = 20)
    private Double longitude;

    @Column(length = 15)
    private String cat1;
    @Column(length = 15)
    private String cat2;
    @Column(length = 15)
    private String cat3;
    @Column(length = 15)
    private String cat4;
    @Column(length = 100)
    private String period;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id")
    public Place place;
}
