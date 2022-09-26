package app.learningtrip.apiserver.dummy_temp;

import app.learningtrip.apiserver.place.dto.response.PlaceThumbnail;
import java.util.ArrayList;
import java.util.List;
import lombok.Data;

@Data
public class Course {
  private long courseId;

  private String name;

  private String imageURL;

  private List<PlaceThumbnail> placeThumbnailList;

  public Course(long courseId, String name, String imageURL, List<PlaceThumbnail> placeThumbnailList) {
    this.courseId = courseId;
    this.name = name;
    this.imageURL = imageURL;
    this.placeThumbnailList = placeThumbnailList;
  }

  public Course(int courseId) {
    if(courseId == 1){
      this.courseId = courseId;
      this.name = "코스1";
      this.imageURL = "http://www.thepingpong.co.kr/news/photo/201711/3746_15394_5227.jpg";
      List<PlaceThumbnail> placeThumbnailList = new ArrayList<PlaceThumbnail>();
제      placeThumbnailList.add(PlaceThumbnail.builder()
          .id(1L)
          .name("숭례문")
          .address("서울 중구")
          .imageURL("http://tong.visitkorea.or.kr/cms/resource/01/1945801_image2_1.jpg")
          .build());
      placeThumbnailList.add(PlaceThumbnail.builder()
          .id(2L)
          .name("국립중앙박물관")
          .address("서울특별시 용산구 서빙고로 137")
          .imageURL("http://tong.visitkorea.or.kr/cms/resource/01/1945801_image2_1.jpg")
          .build());
      this.placeThumbnailList = placeThumbnailList;
    } else {
      this.courseId = courseId;
      this.name = "코스2";
      this.imageURL = "https://t1.daumcdn.net/cfile/tistory/2475813854614D1C22";
      List<PlaceThumbnail> placeThumbnailList = new ArrayList<PlaceThumbnail>();
      placeThumbnailList.add(PlaceThumbnail.builder()
          .id(1L)
          .name("숭례문")
          .address("서울 중구")
          .imageURL("http://tong.visitkorea.or.kr/cms/resource/01/1945801_image2_1.jpg")
          .build());
      placeThumbnailList.add(PlaceThumbnail.builder()
          .id(2L)
          .name("국립중앙박물관")
          .address("서울특별시 용산구 서빙고로 137")
          .imageURL("http://tong.visitkorea.or.kr/cms/resource/01/1945801_image2_1.jpg")
          .build());
      this.placeThumbnailList = placeThumbnailList;
    }
  }
}
