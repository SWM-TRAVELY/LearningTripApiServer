package app.learningtrip.apiserver.course.dto.response;

import app.learningtrip.apiserver.course.domain.Course;
import app.learningtrip.apiserver.place.domain.Place;
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

    private Long id;

    private String name;

    private String imageURL;

    private String place1;

    private String place2;

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
