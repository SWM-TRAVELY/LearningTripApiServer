package app.learningtrip.apiserver.place.domain;

import lombok.Builder;
import javax.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Getter @Setter
@SuperBuilder
@Table(name = "place")
public class Place {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "place_id", unique = true, nullable = false)
    protected int id;
    @Column(length = 3, nullable = false)
    protected int type;
    @Column(length = 100, nullable = false)
    protected String name;
    protected String overview;
    protected String img1;
    protected String img2;

    @Column(length = 100, nullable = false)
    protected String address;
    @Column(length = 20)
    protected double latitude;
    @Column(length = 20)
    protected double longitude;

    @Column(length = 100)
    protected String tel;
    @Column(length = 300)
    protected String info;

    @Column(length = 200)
    protected String restDate;
    @Column(length = 700)
    protected String useTime;

    @Column(length = 300)
    protected String parking;
    protected boolean babyCarriage;
    protected boolean pet;

    protected boolean bookTour;
}
