package app.learningtrip.apiserver.heritage.domain;

import app.learningtrip.apiserver.place.domain.Place;
import app.learningtrip.apiserver.place.domain.Place.PlaceBuilder;
import javax.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
@Builder(builderMethodName = "HeritageBuilder")
@Table
public class Heritage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "heritage_id", unique = true, nullable = false)
    private int id;
    @Column(length = 50, nullable = false)
    private String name;
    @Column(length = 12)
    private String type;
    @Column(nullable = false)
    private String overview;

    @Column(length = 110, nullable = false)
    private String address;
    @Column(length = 20)
    private double latitude;
    @Column(length = 20)
    private double longitude;

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

    public static HeritageBuilder builder() {
        return HeritageBuilder();
    }
}
