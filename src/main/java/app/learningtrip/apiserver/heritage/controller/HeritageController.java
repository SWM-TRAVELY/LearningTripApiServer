package app.learningtrip.apiserver.heritage.controller;

import app.learningtrip.apiserver.heritage.domain.Heritage;
import app.learningtrip.apiserver.place.domain.Place;
import app.learningtrip.apiserver.place.domain.Place_Detail_Tour;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class HeritageController {

    @GetMapping("/heritage/{heritage_id}")
    public Heritage info(@PathVariable(name = "heritage_id") Long heritage_id) {
        Place place = Place_Detail_Tour.builder()
            .type(12)
            .name("숭례문")
            .overview("숭례문(崇禮門)은 조선 태조 5년(1396)에 최초로 축조되었고 1398년 2월 중건되었다. 이 문은 조선시대 한성 도성의 정문으로  4대문 가운데 남쪽에 위치하므로, 남대문으로도 불린다. 1448년에도 크게 고쳐지었다. "
                + "이후 임진왜란과 병자호란 때에도 남대문은 피해를 입지 않았다.처음 만들어졌을 때는 양측에 성벽이 연결되어 있었지만 1908년 도로를 내기 위하여 헐어 내고 성문만 섬처럼 따로 떨어져 있었으나, 2006년 복원 공사를 마치고 지금과 같은 모습을 하게 되었다. "
                + "1962년 문화재보호법에 의하여 보물에서 국보로 지정되었다.<br><br>건물의 평면은 아래.위층이 모두 5칸, 측면 2칸이며,  건물 내부의 아래층 바닥은 홍예의 윗면인 중앙칸만이 우물마루일 뿐, 다른 칸은 흙바닥으로 되어있고 위층은 널마루이다. "
                + "편액의 필자에 관하여는 여러가지 설이 있으나, &lt;지봉유설&gt;에는 양녕대군이 쓴 것이라고 기록되어 있다. 다른 문의 편액은 가로로 쓰여 있으나 숭례문이 세로로 쓰여 있는것은 숭례(崇禮)의 두 글자가 불꽃을 의미하여, 경복궁을 마주보는 관악산의 불기운을 누르게 하기 위해서라고 한다."
                + "<br><br>현존하는 성문 건물로는 우리나라에서 가장 규모가 큰 남대문은 전형적인 다포(多包)양식의 건물로 견실한 목조건축물의 수범을 보이고 있는 한국 건축사상 중요한 건물의 하나이다. "
                + "1997년 초 서울시에서 이 문의 경관을 더욱 돋보이게 하기 위하여 조명설비를 새로이 한 바 있다.서울 중구는 2005년 10월부터 통로 보수공사와 홍예문 입구 등 5곳에 대한 지표조사를 실시, 조선 세종때의 것으로 추정되는 아랫부분 석축 기단과 지대석(맨 아래 기초석), 박석(바닥에 까는 돌), 문지도리(문을 다는 돌 구조물) 등을 발굴했다."
                + "<br><br>이는 1907년께 남대문을 관통하던 전차선로를 내면서 문 주위로 흙을 1미터 가량 쌓아올려 아래쪽 기단과 박석들이 완전히 묻힌 것으로 보인다고 밝혔다. 따라서 중구는 시민들이 지반보다 1.6ｍ 아래에 있는 이들 구조물을 볼 수 있도록 중앙통로 시굴 부분을 그대로 남겨둔 채 관람시설을 설치했다. "
                + "숭례문의 중앙통로인 홍예문을 따라 숭례문을 둘러볼 수 있다. 그러나 숭례문은 2008년 2월 10일 화재로 인하여 전소되었고, 5년 3개월에 걸친 복구 사업을 완료하고 2013년 개방되었다. 2013년 5월 1일에는 숭례문 복구를 고하는 '고유제(告由祭)'가 치러졌다."
                + "숭례문 복원에는 충남 태안의 안면송이 사용되었다. 안면송은 고려시대부터 궁궐이나 선박용으로 사용되어 왔으며, 이번 숭례문의 석가래, 기둥, 지붕 등에 사용되었다.")
            .img1("http://tong.visitkorea.or.kr/cms/resource/01/1945801_image2_1.jpg")
            .img2("http://tong.visitkorea.or.kr/cms/resource/01/1945801_image3_1.jpg")
            .address("서울특별시 중구 세종대로 40")
            .latitude(37.55998551)
            .longitude(126.9752993)
            .tel(null)
            .info("02-779-8547~8")
            .restDate("월요일")
            .useTime("[개방시간] 09:00~18:00<br />\n"
                + "※ 6~8월은 18:30까지 개방<br />\n"
                + "※ 12~2월은 17:30까지 개방")
            .parking(null)
            .babyCarriage(false)
            .pet(false)
            .bookTour(true)
            .expAge(null)
            .expGuide(null)
            .heritageCul(false)
            .heritageNat(false)
            .heritageRec(false)
            .build();

        Heritage heritage = Heritage.builder()
            .name("서울 숭례문")
            .type("국보")
            .overview("조선시대 한양도성의 정문으로 남쪽에 있다고 해서 남대문이라고도 불렀다. 현재 서울에 남아 있는 목조 건물 중 가장 오래된 것으로 태조 5년(1396)에 짓기 시작하여 태조 7년(1398)에 완성하였다. 이 건물은 세종 30년(1448)에 고쳐 지은 것인데 1961∼1963년 해체·수리 때 성종 10년(1479)에도 큰 공사가 있었다는 사실이 밝혀졌다. 이후, 2008년 2월 10일 숭례문 방화 화재로 누각 2층 지붕이 붕괴되고 1층 지붕도 일부 소실되는 등 큰 피해를 입었으며, 5년 2개월에 걸친 복원공사 끝에 2013년 5월 4일 준공되어 일반에 공개되고 있다.\n"
                + " 이 문은 돌을 높이 쌓아 만든 석축 가운데에 무지개 모양의 홍예문을 두고, 그 위에 앞면 5칸·옆면 2칸 크기로 지은 누각형 2층 건물이다. 지붕은 앞면에서 볼 때 사다리꼴 형태를 하고 있는데, 이러한 지붕을 우진각지붕이라 한다. 지붕 처마를 받치기 위해 기둥 위부분에 장식하여 짠 구조가 기둥 위뿐만 아니라 기둥 사이에도 있는 다포 양식으로, 그 형태가 곡이 심하지 않고 짜임도 건실해 조선 전기의 특징을 잘 보여주고 있다.\n"
                + " 『지봉유설』의 기록에는 ‘숭례문’이라고 쓴 현판을 양녕대군이 썼다고 한다. 지어진 연대를 정확히 알 수 있는 서울 성곽 중에서 제일 오래된 목조 건축물이다.\n"
                + "Ο 숭례문 방화 화재(2008.2.10)\n"
                + "\u00AD\u00AD\u00AD2008년 숭례문 방화 사건(崇禮門放火事件)은 2008년 2월 10일 ~ 2월 11일 숭례문 건물이 방화로 타 무너진 사건이다. 화재는 2008년 2월 10일 오후 8시 40분 전후에 발생하여 다음날인 2008년 2월 11일 오전 0시 40분경 숭례문의 누각 2층 지붕이 붕괴하였고 이어 1층에도 불이 붙어 화재 5시간 만인 오전 1시 55분쯤 석축을 제외한 건물이 훼손되었다.")
            .address("서울 중구 세종대로 40")
            .latitude(37.55997522)
            .longitude(126.9753127)
            .cat1("유적건조물")
            .cat2("정치국방")
            .cat3("성")
            .cat4("성곽시설")
            .period("조선 태조 7년(1398)")
            .place(place)
            .build();
        return heritage;
    }
}
