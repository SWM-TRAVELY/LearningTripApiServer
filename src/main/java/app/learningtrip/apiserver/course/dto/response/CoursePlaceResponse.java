package app.learningtrip.apiserver.course.dto.response;

import app.learningtrip.apiserver.course.domain.Course;
import app.learningtrip.apiserver.course.domain.CoursePlace;
import app.learningtrip.apiserver.place.domain.Place;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter @Setter @Builder
public class CoursePlaceResponse {

    Long id;

    String name;

    String description;

    String imageURL;

    String address;

    Integer day;

    Integer sequence;

    Double distance;

    public static CoursePlaceResponse toResponse(CoursePlace coursePlace, Double distance) {

        return CoursePlaceResponse.builder()
            .id(coursePlace.place.getId())
            .name(coursePlace.place.getName())
            .description(coursePlace.place.getDescription())
            .imageURL(coursePlace.place.getImageURL1())
            .address(coursePlace.place.getAddress())
            .day(coursePlace.getDay())
            .sequence(coursePlace.getSequence())
            .distance(distance)
            .build();
    }
}
