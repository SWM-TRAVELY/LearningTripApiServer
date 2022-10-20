package app.learningtrip.apiserver.course.dto.response;

import app.learningtrip.apiserver.course.domain.Course;
import app.learningtrip.apiserver.place.domain.Place;
import io.swagger.annotations.ApiModelProperty;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Data
@Getter @Setter @Builder
@AllArgsConstructor @NoArgsConstructor
public class CourseThumbnail {

    @ApiModelProperty(example = "코스 아이디: Long")
    private Long id;

    @ApiModelProperty(example = "코스 이름: String")
    private String name;

    @ApiModelProperty(example = "코스 대표사진: String")
    private String imageURL;

    @ApiModelProperty(example = "코스에 포함된 관광지1: String")
    private String place1;

    @ApiModelProperty(example = "코스에 포함된 관광지2: String")
    private String place2;

    @ApiModelProperty(example = "코스에 포함된 관광지3: String")
    private String place3;

    public static CourseThumbnail toThumbnail(Course course, List<Place> placeList) {
        CourseThumbnail courseThumbnail = CourseThumbnail.builder()
            .id(course.getId())
            .name(course.getName())
            .build();

        // place 예시 3개 지정
        courseThumbnail.setPlace1("");
        courseThumbnail.setPlace2("");
        courseThumbnail.setPlace3("");
        if (placeList.size() > 0) {
            courseThumbnail.setPlace1(placeList.get(0).getName());
        }
        if (placeList.size() > 1) {
            courseThumbnail.setPlace2(placeList.get(1).getName());
        }
        if (placeList.size() > 2) {
            courseThumbnail.setPlace3(placeList.get(2).getName());
        }

        // course image 지정
        for (Place place : placeList) {
            String imageURL = place.getImageURL1();
            if (!imageURL.equals("")) {
                System.out.println(place.getName() + " : " + place.getImageURL1());
                courseThumbnail.setImageURL(imageURL);
                break;
            }
            System.out.println("imageURL empty");
        }

        return courseThumbnail;
    }
}
