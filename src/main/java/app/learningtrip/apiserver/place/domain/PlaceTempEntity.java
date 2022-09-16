package app.learningtrip.apiserver.place.domain;

import app.learningtrip.apiserver.place.dto.response.PlaceInfoResponse;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

// Entity 어노테이션 관련 정리글
// https://velog.io/@leyuri/Spring-boot-JPA-%EC%96%B4%EB%85%B8%ED%85%8C%EC%9D%B4%EC%85%98-Entity-Table-Column-Id-Lombok

// 얘는 response 형태로 테이블 임시로 짜뒀어요 수정해야함!!!

@Entity
@Table(name = "PlaceTemp")
public class PlaceTempEntity {
    @Id
    @Column(name = "place_id")
    private long placeId;

    @Column(name = "type_id")
    private int typeId;

    @Column(name = "place_name")
    private String placeName;

    @Column(name = "address")
    private String address;

    @Column(name = "chk_in_textbook")
    private boolean chkInTextbook;

    @Column(name = "latitude")
    private float latitude;

    @Column(name = "longitude")
    private float longitude;

    @Column(name = "tel")
    private String tel;

    @Column(name = "overview")
    private String overview;

    @Column(name = "image")
    private String image;

    @Column(name = "rest_date")
    private String restDate;

    @Column(name = "use_time")
    private String useTime;

    @Column(name = "chk_parking")
    private boolean chkParking;

    @Column(name = "chk_baby_carriage")
    private boolean chkBabyCarriage;

    @Column(name = "chk_pets")
    private boolean chkPets;

    @Column(name = "age_available")
    private String ageAvailable;

    @Column(name = "exp_guide")
    private String expGuide;

    @Column(name = "chk_world_cultural")
    private boolean chkWorldCultural;

    @Column(name = "chk_world_natural")
    private boolean chkWorldNatural;

    @Column(name = "chk_world_record")
    private boolean chkWorldRecord;

    public PlaceInfoResponse toPlaceInfoResponse(){
        return PlaceInfoResponse.builder()
                .placeId(placeId)
                .typeId(typeId)
                .placeName(placeName)
                .address(address)
                .chkInTextbook(chkInTextbook)
                .latitude(latitude)
                .longitude(longitude)
                .tel(tel)
                .overview(overview)
                .image(image)
                .restDate(restDate)
                .useTime(useTime)
                .chkParking(chkParking)
                .chkBabyCarriage(chkBabyCarriage)
                .chkPets(chkPets)
                .ageAvailable(ageAvailable)
                .expGuide(expGuide)
                .chkWorldCultural(chkWorldCultural)
                .chkWorldNatural(chkWorldNatural)
                .chkWorldRecord(chkWorldRecord)
                .build();
    }
}
