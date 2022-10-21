package app.learningtrip.apiserver.course.dto.response;

import app.learningtrip.apiserver.course.domain.CoursePlace;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter @Setter @Builder
public class CoursePlaceResponse {

    @ApiModelProperty(value = "관광지 아이디", example = "1")
    private Long id;

    @ApiModelProperty(example = "관광지 이름: String")
    private String name;

    @ApiModelProperty(example = "관광지 설명: String")
    private String description;

    @ApiModelProperty(example = "관광지 대표 이미지: String")
    private String imageURL;

    @ApiModelProperty(example = "관광지 주소: String")
    private String address;

    @ApiModelProperty(example = "관광지를 방문할 일차: Integer")
    private Integer day;

    @ApiModelProperty(example = "해당 일차의 관광 순서: Interger")
    private Integer sequence;

    @ApiModelProperty(example = "앞의 관광지와의 거리: Double")
    private Double distance;

    @ApiModelProperty(example = "앞의 관광지와의 시간: Double")
    private Integer time;

    public static CoursePlaceResponse toResponse(CoursePlace coursePlace, Double distance, Integer time) {

        return CoursePlaceResponse.builder()
            .id(coursePlace.place.getId())
            .name(coursePlace.place.getName())
            .description(coursePlace.place.getDescription())
            .imageURL(coursePlace.place.getImageURL1())
            .address(coursePlace.place.getAddress())
            .day(coursePlace.getDay())
            .sequence(coursePlace.getSequence())
            .distance(distance)
            .time(time)
            .build();
    }
}
