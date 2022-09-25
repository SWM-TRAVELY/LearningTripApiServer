package app.learningtrip.apiserver.place.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
public class PlaceThumbnail {
  private long id;

  private String name;

  private String province;

  private String imageURL;

  @Builder
  public PlaceThumbnail(long id, String name, String province, String imageURL) {
    this.id = id;
    this.name = name;
    this.province = province;
    this.imageURL = imageURL;
  }

  public PlaceThumbnail(int code){
    if(code == 1){
      this.id = 128162;
      this.name = "숭례문";
      this.province = "서울 중구";
      this.imageURL = "http://tong.visitkorea.or.kr/cms/resource/01/1945801_image2_1.jpg";
    } else if (code == 2) {
      this.id = 126508;
      this.name = "경복궁";
      this.province = "서울 종로구";
      this.imageURL = "http://tong.visitkorea.or.kr/cms/resource/23/2678623_image2_1.jpg";
    } else if (code == 3) {
      this.id = 126510;
      this.name = "종묘";
      this.province = "서울 중구";
      this.imageURL = "http://tong.visitkorea.or.kr/cms/resource/41/2678541_image2_1.jpg";
    } else {
      this.id = 127642;
      this.name = "창덕궁과 후원";
      this.province = "서울 중구";
      this.imageURL = "http://tong.visitkorea.or.kr/cms/resource/47/2678647_image2_1.jpg";
    }
  }
}
