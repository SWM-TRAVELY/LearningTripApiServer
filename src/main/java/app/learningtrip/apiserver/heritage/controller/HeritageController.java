package app.learningtrip.apiserver.heritage.controller;

import app.learningtrip.apiserver.heritage.domain.Heritage;
import app.learningtrip.apiserver.heritage.dto.response.HeritageResponse;
import app.learningtrip.apiserver.heritage.dto.response.HeritageThumbnailResponse;
import app.learningtrip.apiserver.heritage.service.HeritageService;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class HeritageController {

    @Autowired
    private HeritageService heritageService;

    @GetMapping("/heritage/sieun/{heritage_id}")
    public ResponseEntity<Optional<Heritage>> testHeritage(@PathVariable(name = "heritage_id") long heritage_id) {

        Optional<Heritage> heritage = heritageService.findOne(heritage_id);
        return ResponseEntity.ok().body(heritage);
    }

    @GetMapping("/heritage/{heritage_id}")
    public HeritageResponse info(@PathVariable(name = "heritage_id") Long heritage_id) {
        HeritageResponse heritageResponse = HeritageResponse.builder()
            .id(1)
            .name("서울 숭례문")
            .imageURL("http://tong.visitkorea.or.kr/cms/resource/01/1945801_image2_1.jpg")
            .type("국보")
            .description("조선시대 한양도성의 정문으로 남쪽에 있다고 해서 남대문이라고도 불렀다. 현재 서울에 남아 있는 목조 건물 중 가장 오래된 것으로 태조 5년(1396)에 짓기 시작하여 태조 7년(1398)에 완성하였다. 이 건물은 세종 30년(1448)에 고쳐 지은 것인데 1961∼1963년 해체·수리 때 성종 10년(1479)에도 큰 공사가 있었다는 사실이 밝혀졌다. 이후, 2008년 2월 10일 숭례문 방화 화재로 누각 2층 지붕이 붕괴되고 1층 지붕도 일부 소실되는 등 큰 피해를 입었으며, 5년 2개월에 걸친 복원공사 끝에 2013년 5월 4일 준공되어 일반에 공개되고 있다.\n"
                + " 이 문은 돌을 높이 쌓아 만든 석축 가운데에 무지개 모양의 홍예문을 두고, 그 위에 앞면 5칸·옆면 2칸 크기로 지은 누각형 2층 건물이다. 지붕은 앞면에서 볼 때 사다리꼴 형태를 하고 있는데, 이러한 지붕을 우진각지붕이라 한다. 지붕 처마를 받치기 위해 기둥 위부분에 장식하여 짠 구조가 기둥 위뿐만 아니라 기둥 사이에도 있는 다포 양식으로, 그 형태가 곡이 심하지 않고 짜임도 건실해 조선 전기의 특징을 잘 보여주고 있다.\n"
                + " 『지봉유설』의 기록에는 ‘숭례문’이라고 쓴 현판을 양녕대군이 썼다고 한다. 지어진 연대를 정확히 알 수 있는 서울 성곽 중에서 제일 오래된 목조 건축물이다.\n"
                + "Ο 숭례문 방화 화재(2008.2.10)\n"
                + "\u00AD\u00AD\u00AD2008년 숭례문 방화 사건(崇禮門放火事件)은 2008년 2월 10일 ~ 2월 11일 숭례문 건물이 방화로 타 무너진 사건이다. 화재는 2008년 2월 10일 오후 8시 40분 전후에 발생하여 다음날인 2008년 2월 11일 오전 0시 40분경 숭례문의 누각 2층 지붕이 붕괴하였고 이어 1층에도 불이 붙어 화재 5시간 만인 오전 1시 55분쯤 석축을 제외한 건물이 훼손되었다.")
            .category("성곽시설")
            .build();
        return heritageResponse;
    }

    @GetMapping("/heritage/related/{place_id}")
    public List<HeritageThumbnailResponse> related(@PathVariable(name = "place_id") Long place_id) {
        HeritageThumbnailResponse heritageThumbnail_1 = HeritageThumbnailResponse.builder()
            .id(1)
            .name("문화재1")
            .imageURL("http://tong.visitkorea.or.kr/cms/resource/01/1945801_image2_1.jpg")
            .build();
        HeritageThumbnailResponse heritageThumbnail_2 = HeritageThumbnailResponse.builder()
            .id(2)
            .name("문화재2")
            .imageURL("http://tong.visitkorea.or.kr/cms/resource/01/1945801_image2_1.jpg")
            .build();
        List<HeritageThumbnailResponse> heritageThumbnailResponseList = new ArrayList<HeritageThumbnailResponse>();
        heritageThumbnailResponseList.add(heritageThumbnail_1);
        heritageThumbnailResponseList.add(heritageThumbnail_2);

        return heritageThumbnailResponseList;
    }
}
