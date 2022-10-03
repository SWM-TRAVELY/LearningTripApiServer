package app.learningtrip.apiserver.place.service;

import app.learningtrip.apiserver.place.domain.Place;
import app.learningtrip.apiserver.place.domain.PlaceDetailCulture;
import app.learningtrip.apiserver.place.domain.PlaceDetailTour;
import app.learningtrip.apiserver.place.dto.response.PlaceThumbnail;
import app.learningtrip.apiserver.place.dto.response.PlaceDetailCultureResponse;
import app.learningtrip.apiserver.place.dto.response.PlaceDetailTourResponse;
import app.learningtrip.apiserver.place.dto.response.PlaceResponse;
import app.learningtrip.apiserver.place.repository.PlaceDetailCultureRepository;
import app.learningtrip.apiserver.place.repository.PlaceDetailTourRepository;
import app.learningtrip.apiserver.place.repository.PlaceRepository;
import java.rmi.NoSuchObjectException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class PlaceService {

    private final PlaceRepository placeRepository;
    private final PlaceDetailTourRepository placeDetailTourRepository;
    private final PlaceDetailCultureRepository placeDetailCultureRepository;

    static final int TOUR = 12;
    static final int CULTURE = 14;

    /**
     * place 조회
     */
    public Optional<PlaceResponse> getInfo(long id) throws NoSuchObjectException, NoSuchElementException {

        // place table 조회
        Optional<Place> place = placeRepository.findById(id);
        if (place.isPresent() == false) {
            return getInfoDummy(id);
        }
        //place.orElseThrow(() -> new NoSuchElementException("존재하지 않은 Place입니다."));

        // PlaceResponse로 변환: type별 다른 placeDetailResponse 생성
        if (place.get().getType() == TOUR) {
            Optional<PlaceDetailTour> placeDetailTour = placeDetailTourRepository.findById(
                place.get().getId());
            placeDetailTour.orElseThrow(() -> new NoSuchObjectException("올바르지 않은 type의 DB에 조회했습니다."));

            PlaceDetailTourResponse placeDetailTourResponse = PlaceDetailTourResponse.toResponse(placeDetailTour.get());

            return Optional.ofNullable(placeDetailTourResponse);
        } else if (place.get().getType() == CULTURE) {
            Optional<PlaceDetailCulture> placeDetailCulture = placeDetailCultureRepository.findById(
                place.get().getId());
            placeDetailCulture.orElseThrow(() -> new NoSuchObjectException("올바르지 않은 type의 DB에 조회했습니다."));

            PlaceDetailCultureResponse placeDetailCultureResponse = PlaceDetailCultureResponse.toResponse(placeDetailCulture.get());

            return Optional.ofNullable(placeDetailCultureResponse);
        } else {

            throw new NoSuchFieldError("place의 type이 잘못되었습니다.");
        }
    }

    /**
     * 유사 관광지 조회
     */
    public List<PlaceThumbnail> getSimilar(long place_id) {
        List<PlaceThumbnail> placeThumbnailList = getNearby(place_id);

        return placeThumbnailList;
    }

    /**
     * 주변 관광지 조회
     * @param place_id 관광지 id
     * @return 관광지 id (경도, 위도) 주변의 반경 5000m 관광지 썸네일 리스트 (최대 8개)
     */
    public List<PlaceThumbnail> getNearby(long place_id){
        //
        Place p = placeRepository.findById(place_id).orElseThrow(null);

        List<Place> placeList = placeRepository.findPlacesByDistance(p.getLongitude(),
            p.getLatitude(), PageRequest.of(0,9)).orElseThrow(null);

        List<PlaceThumbnail> placeThumbnailList = new ArrayList<>();

        placeList.forEach(place -> {
            if (place.getId() != place_id){
               placeThumbnailList.add(place.toPlaceThumbnail());
            }
        });

        return placeThumbnailList;
    }

    /**
     * 추천 관광지
     */
    public List<PlaceThumbnail> getRecommend() {
        try {
            List<PlaceThumbnail> placeThumbnailList = getRandom();

            return placeThumbnailList;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * random 관광지
     */
    public List<PlaceThumbnail> getRandom() throws NoSuchObjectException {
        int countOfPlace = placeRepository.countAllBy();

        List<Integer> duplicationCheckList = new ArrayList<Integer>();
        List<PlaceThumbnail> placeThumbnailList = new ArrayList<PlaceThumbnail>();

        for (int i = 0; i < 4; i++) {
            int index = (int)(Math.random() * countOfPlace) + 1;

            // 중복 체크
            int flag = 0;
            for (int listLength = 0; listLength < duplicationCheckList.size(); listLength++) {
                if (index == duplicationCheckList.get(listLength)) {
                    flag = 1;
                    break;
                }
            }
            if (flag == 1) {
                i--;
                continue;
            }

            duplicationCheckList.add(index);

            Optional<Place> place = placeRepository.findById(Long.valueOf(index));
            place.orElseThrow(() -> new NoSuchObjectException("없는 관광지를 조회했습니다."));

            PlaceThumbnail placeThumbnail = PlaceThumbnail.builder()
                .id(place.get().getId())
                .name(place.get().getName())
                .address(place.get().getAddress())
                .imageURL(place.get().getImageURL1())
                .build();
            placeThumbnailList.add(placeThumbnail);
        }

    /**
     * 추천 코스 생성할 때 필요한 Place 생성
     */
    public List<String> getSeoulTextbookPlace() {
        List<String> place = new ArrayList<String>();
        place.add("암사동선사유적박물관");
        place.add("서울 석촌동 고분군");
        place.add("서울 풍납동 토성");
        place.add("국립중앙박물관");
        place.add("화폐박물관");

        place.add("고려대학교박물관");
        place.add("국립고궁박물관");
        place.add("동국대학교 박물관");
        place.add("서울대학교 규장각");
        place.add("경복궁");

        place.add("숭례문");
        place.add("종묘 [유네스코 세계문화유산]");
        place.add("국립한글박물관");
        place.add("숭실대학교 한국기독교박물관");
        place.add("서울역사박물관");

        place.add("간송미술관(서울 보화각)");
        place.add("서울대학교 박물관");
        place.add("독립문");

        return place;
    }

    public void setSeoulTextbookPlace() {
        String imageURL = "https://i.ibb.co/QvY3mqG/image.png";

        List<String> placeName = getSeoulTextbookPlace();

        List<String> placeAddress = new ArrayList<String>();
        placeAddress.add("서울특별시 강동구 올림픽로 875");
        placeAddress.add("서울특별시 송파구 가락로7길 21");
        placeAddress.add("서울특별시 송파구 강동대로 74");
        placeAddress.add("서울특별시 용산구 서빙고로 137");
        placeAddress.add("서울특별시 중구 남대문로 39");

        placeAddress.add("서울특별시 성북구 안암로 145");
        placeAddress.add("서울특별시 종로구 효자로 12");
        placeAddress.add("서울특별시 중구 필동로1길 30");
        placeAddress.add("서울특별시 관악구 관악로 1");
        placeAddress.add("서울특별시 종로구 사직로 161");

        placeAddress.add("서울특별시 중구 세종대로 40");
        placeAddress.add("서울특별시 종로구 종로 157");
        placeAddress.add("서울특별시 용산구 서빙고로 139");
        placeAddress.add("서울특별시 동작구 상도로 369");
        placeAddress.add("서울특별시 종로구 새문안로 55");

        placeAddress.add("서울특별시 중구 을지로 281");
        placeAddress.add("서울특별시 관악구 관악로 1");
        placeAddress.add("서울특별시 서대문구 통일로 251");

        List<Integer> placeType = new ArrayList<Integer>();
        placeType.add(14);
        placeType.add(12);
        placeType.add(12);
        placeType.add(14);
        placeType.add(14);

        placeType.add(14);
        placeType.add(14);
        placeType.add(14);
        placeType.add(14);
        placeType.add(12);

        placeType.add(12);
        placeType.add(12);
        placeType.add(14);
        placeType.add(14);
        placeType.add(14);

        placeType.add(14);
        placeType.add(14);
        placeType.add(12);

        for (int i = 0; i < 18; i++) {
            Place makePlace = Place.builder()
                .type(placeType.get(i))
                .name(placeName.get(i))
                .address(placeAddress.get(i))
                .imageURL1(imageURL)
                .description("--")
                .build();
            placeRepository.save(makePlace);
        }
    }

    public List<String> getGyeongjuTextbookPlace() {
        List<String> place = new ArrayList<String>();
        place.add("국립경주박물관");
        place.add("경주 첨성대");
        place.add("경주 감은사지");
        place.add("경주 석굴암 [유네스코 세계문화유산]");
        place.add("경주 불국사 [유네스코 세계문화유산]");
        place.add("경주 원성왕릉");

        return place;
    }

    public void setGyeongjuTextbookPlace() {
        String imageURL = "https://i.ibb.co/QvY3mqG/image.png";

        List<String> placeName = getGyeongjuTextbookPlace();

        List<String> placeAddress = new ArrayList<String>();
        placeAddress.add("경상북도 경주시 일정로 186");
        placeAddress.add("경상북도 경주시 첨성로 140-25");
        placeAddress.add("경상북도 경주시 양북면 용당리 55-1");
        placeAddress.add("경상북도 경주시 진현동 불국로 873-243");
        placeAddress.add("경상북도 경주시 불국로 385");
        placeAddress.add("경상북도 경주시 외동읍 신계입실길 139");

        List<Integer> placeType = new ArrayList<Integer>();
        placeType.add(14);
        placeType.add(12);
        placeType.add(12);
        placeType.add(12);
        placeType.add(12);
        placeType.add(12);

        for (int i = 0; i < 6; i++) {
            Place makePlace = Place.builder()
                .id(Integer.toUnsignedLong(i))
                .type(placeType.get(i))
                .name(placeName.get(i))
                .address(placeAddress.get(i))
                .imageURL1(imageURL)
                .description("--")
                .build();
            placeRepository.save(makePlace);
        }
    }

    public List<Place> findSeoulTextbookPlace() {
        List<String> placeNameList = getSeoulTextbookPlace();

        List<Place> placeList = new ArrayList<Place>();
        for (String placeName : placeNameList) {
            Optional<Place> place = Optional.ofNullable(placeRepository.findByName(placeName));
            if (place.isPresent() == false) {
                continue;
            }
            placeList.add(place.get());
        }
        return placeList;
    }
    /**
     * Dummy Data
     */
    public Optional<PlaceResponse> getInfoDummy(long place_id) {
        if (place_id == 12){
            return Optional.ofNullable(PlaceDetailTourResponse.builder()
                .id(place_id)
                .name("숭례문")
                .description("숭례문(崇禮門)은 조선 태조 5년(1396)에 최초로 축조되었고 1398년 2월 중건되었다. 이 문은 조선시대 한성 도성의 정문으로  4대문 가운데 남쪽에 위치하므로, 남대문으로도 불린다. 1448년에도 크게 고쳐지었다. "
                    + "이후 임진왜란과 병자호란 때에도 남대문은 피해를 입지 않았다.처음 만들어졌을 때는 양측에 성벽이 연결되어 있었지만 1908년 도로를 내기 위하여 헐어 내고 성문만 섬처럼 따로 떨어져 있었으나, 2006년 복원 공사를 마치고 지금과 같은 모습을 하게 되었다. "
                    + "1962년 문화재보호법에 의하여 보물에서 국보로 지정되었다.<br><br>건물의 평면은 아래.위층이 모두 5칸, 측면 2칸이며,  건물 내부의 아래층 바닥은 홍예의 윗면인 중앙칸만이 우물마루일 뿐, 다른 칸은 흙바닥으로 되어있고 위층은 널마루이다. "
                    + "편액의 필자에 관하여는 여러가지 설이 있으나, &lt;지봉유설&gt;에는 양녕대군이 쓴 것이라고 기록되어 있다. 다른 문의 편액은 가로로 쓰여 있으나 숭례문이 세로로 쓰여 있는것은 숭례(崇禮)의 두 글자가 불꽃을 의미하여, 경복궁을 마주보는 관악산의 불기운을 누르게 하기 위해서라고 한다."
                    + "<br><br>현존하는 성문 건물로는 우리나라에서 가장 규모가 큰 남대문은 전형적인 다포(多包)양식의 건물로 견실한 목조건축물의 수범을 보이고 있는 한국 건축사상 중요한 건물의 하나이다. "
                    + "1997년 초 서울시에서 이 문의 경관을 더욱 돋보이게 하기 위하여 조명설비를 새로이 한 바 있다.서울 중구는 2005년 10월부터 통로 보수공사와 홍예문 입구 등 5곳에 대한 지표조사를 실시, 조선 세종때의 것으로 추정되는 아랫부분 석축 기단과 지대석(맨 아래 기초석), 박석(바닥에 까는 돌), 문지도리(문을 다는 돌 구조물) 등을 발굴했다."
                    + "<br><br>이는 1907년께 남대문을 관통하던 전차선로를 내면서 문 주위로 흙을 1미터 가량 쌓아올려 아래쪽 기단과 박석들이 완전히 묻힌 것으로 보인다고 밝혔다. 따라서 중구는 시민들이 지반보다 1.6ｍ 아래에 있는 이들 구조물을 볼 수 있도록 중앙통로 시굴 부분을 그대로 남겨둔 채 관람시설을 설치했다. "
                    + "숭례문의 중앙통로인 홍예문을 따라 숭례문을 둘러볼 수 있다. 그러나 숭례문은 2008년 2월 10일 화재로 인하여 전소되었고, 5년 3개월에 걸친 복구 사업을 완료하고 2013년 개방되었다. 2013년 5월 1일에는 숭례문 복구를 고하는 '고유제(告由祭)'가 치러졌다."
                    + "숭례문 복원에는 충남 태안의 안면송이 사용되었다. 안면송은 고려시대부터 궁궐이나 선박용으로 사용되어 왔으며, 이번 숭례문의 석가래, 기둥, 지붕 등에 사용되었다.")
                .imageURL("http://tong.visitkorea.or.kr/cms/resource/01/1945801_image2_1.jpg")
                .address("서울특별시 중구 세종대로 40")
                .latitude(37.55998551)
                .longitude(126.9752993)
                .tel("")
                .restDate("월요일")
                .useTime("[개방시간] 09:00~18:00<br />\n"
                    + "※ 6~8월은 18:30까지 개방<br />\n"
                    + "※ 12~2월은 17:30까지 개방")
                .parking("")
                .babyCarriage(false)
                .pet(false)
                .textbook(true)
                .experienceAge("")
                .experienceInfo("")
                .worldCulturalHeritage(false)
                .worldNaturalHeritage(false)
                .worldRecordHeritage(false)
                .build());
        } else {
            return Optional.ofNullable(PlaceDetailCultureResponse.builder()
                .id(place_id)
                .name("국립중앙박물관")
                .description("국립중앙박물관은 42만 점의 소장유물을 소장하고 있으며, 고고, 역사, 미술, 기증, 아시아 관련 문화재를 전시하는 상설 전시실과 다양한 전시가 가능하도록 가변성 있게 구성된 기획 전시실, 체험과 참여 학습을 통해 전시를 이해하도록 설계된 어린이 박물관, 박물관 야외정원을 이용하여 석탑 등 다양한 석조유물을 전시한 야외전시실로 이뤄진다. "
                    + "국립중앙박물관은 국내·외 전시활동 외에도 유물의 수집과 보존, 조사연구, 사회교육활동, 학술자료발간, 국제문화교류활동, 각종 공연 등의 기회를 제공하는 복합문화공간으로서 교육적 측면 뿐 아니라 친환경 녹색공간과 휴게시설 및 양질의 문화 프로그램도 함께 마련되어 있어  남녀노소를 불문하고 언제든 찾아가고 싶은 새로운 도심 속 명소의  역할을 하고 있다. "
                    + "국립중앙박물관은 정원의 폭포와 아름다운 전경으로도 유명하며, 산책을 즐기기에도 그만이다. 전시관 내에 카페테리아와 휴게공간, 아트숍, 식당가, 편의점 등도 편리하게 이용할 수 있다.<br><br><strong>[나주 서성문 안 석등(보물/1963.01.21)] </strong><br>본래 전라남도 나주읍 서문 안에 있던 것을 1929년에 경복궁으로 옮겨놓은 것이다. "
                    + "현재는 국립중앙박물관에 옮겨져 있다. 석등은 불을 밝혀두는 화사석(火舍石)을 중심으로, 아래는 3단을 이루는 받침을 두고 위로는 지붕돌과 머리장식을 얹었다. 네모난 모양의 널찍한 바닥돌 위에 세워져 있으며, 아래받침돌은 8각이고 연꽃문양이 새겨져 있다. 기둥모양의 중간받침은 8면으로 각 면마다 테를 둘러 공간을 만들고 그 중심 안에 한 줄씩의 문장을 새겼다. "
                    + "윗받침돌은 8각면에 돌아가며 연꽃무늬를 조각했고, 화사석은 새로 만들어 놓은 것으로 창이 4개다. 지붕돌은 매우 장식적으로 8개 면마다 처마 끝에 짧은 막을 드리운 것처럼 세로줄무늬가 있고, 그 위로 막 피어오르는 형상의 꽃장식이 두툼하게 달려있다. 지붕돌 위로는 마치 지붕을 축소해 놓은 듯한 돌이 올려져 있고, 맨 꼭대기에 올려진 연꽃봉오리 모양의 돌은 석등을 옮겨 세울 때 새로 만든 것이다. "
                    + "받침에 새겨져 있는 기록을 통해 고려 선종 10년(1093)에 석등을 세웠음을 알 수 있다.각 부분의 조각이 둔중한 편이나 지붕돌의 형태가 장식적인 공예기법을 보이고 있는 특이한 고려시대 작품으로, 통일신라시대 8각형 석등의 양식을 이어받으면서도 그 구조와 조각이 우수하다.<br><br><strong>[원주 흥법사지 염거화상탑(국보/1962.12.20)]</strong><br>"
                    + "통일신라 말의 승려 염거화상의 사리탑이다. 염거화상(?∼844)은 도의선사의 제자로, 선(禪)에 대한 이해가 거의 없었던 당시 주로 설악산 억성사에 머물며 선을 널리 알리는데 힘썼다. 체징에게 그 맥을 전하여 터전을 마련한 뒤 문성왕 6년(844)에 입적하였다. "
                    + "이 탑은 원래 강원도 흥법사터에 서 있었다 하나, 이에 대한 확실한 근거가 없기 때문에 탑이름 앞에 ‘전(傳)’자를 붙이게 되었고, 원래 위치에서 서울로 옮겨진 후에도 탑골공원 등 여러 곳에 옮겨졌다가 경복궁에 세워졌으나 현재 국립중앙박물관에 옮겨져 있다.탑은 아래위 각 부분이 8각의 평면을 기본으로 삼고 있다. "
                    + "기단(基壇)은 밑돌·가운데돌·윗돌의 세 부분으로 이루어져 있으며, 각 면마다 소박한 조각이 멋스럽게 펼쳐져 있다. 밑돌에는 사자를 도드라지게 새겼고, 가운데돌에는 움푹 새긴 안상(眼象)안에 향로를 새겨 두었다.<br><br>2단으로 마련된 윗돌은 아래단에는 연꽃을 두 줄로 돌려 우아함을 살리고 윗단에는 둥그스름한 안상(眼象) 안에 여러 조각을 두어 장식하였다. "
                    + "사리를 모셔둔 탑신(塔身)의 몸돌은 면마다 문짝모양, 4천왕상(四天王像)을 번갈아가며 배치하였는데, 입체감을 잘 살려 사실적으로 표현하였다. 지붕돌은 당시의 목조건축양식을 특히 잘 따르고 있어서 경사면에 깊게 패인 기왓골, 기와의 끝마다 새겨진 막새기와 모양, 밑면의 서까래표현 등은 거의 실제 건물의 기와지붕을 보고 있는 듯하다. 꼭대기에 있는 머리장식은 탑을 옮기기 전까지 남아 있었으나, 지금은 모두 없어졌다. "
                    + "탑을 옮겨 세울 때 그 안에서 금동탑지(金銅塔誌)가 발견되었는데, 이를 통해 통일신라 문성왕 6년(844)에 이 탑을 세웠음을 알게 되었다. 사리탑 중에서는 가장 오래된 것으로, 규모는 그리 크지 않으나 단아한 기품과 깨끗한 솜씨가 잘 어우러져 있다. 이후 대부분의 사리탑이 이 양식을 따르고 있어 그 최초의 의의를 지니는 작품이다.")
                .imageURL("http://tong.visitkorea.or.kr/cms/resource/72/2658572_image2_1.jpg")
                .address("서울특별시 용산구 서빙고로 137")
                .latitude(37.52392312)
                .longitude(126.9803332)
                .tel("02-2077-9000")
                .restDate("휴관일: 1월1일, 설날, 추석<br>상설전시실 정기휴실일: 매년 4월, 11월(첫째 월요일)<br>2022년 휴실일: 4.4.(월), 11.7.(월)<br>기획전시실(특별전시 종료시 휴실), 야외전시장은 정상 개관")
                .useTime("월, 화, 목, 금, 일요일: 10:00 ~ 18:00 (입장 마감: 17:30)<br>수, 토요일: 10:00 ~ 21:00 (입장 마감: 20:30)<br>옥외 전시장(정원)은 오전 7시부터 관람 가능")
                .parking("* 옥내주차장 : 754대<br />\n"
                    + "지하1층 : 관람객용 522대(장애인용 31대, 경차용 26대 포함)<br />\n"
                    + "지상1층 : 업무용 132대(장애인용 10대, 경차용 7대 포함)<br />\n"
                    + "지상2층 : 업무용 100대(장애인용 2대, 경차용 5대 포함)<br />\n"
                    + "* 옥외주차장 : 99대(대형 78대포함)<br />\n"
                    + "※ 주차공간 부족하니 가능한 대중교통 이용바람")
                .babyCarriage(false)
                .pet(false)
                .textbook(true)
                .discount("")
                .parkingFee("승용차(15인승 이하) - 기본 2시간 2,000원 / 초과요금 매 30분당 500원 / 1일 최대(06:00~23:00) 10,000원<br />\n"
                    + "중/대형차(16인승 이상) - 기본 2시간 4,000원 / 초과요금 매 30분당 1,000원 / 1일 최대(06:00~23:00) 20,000원<br />\n"
                    + "※ 입차 후 20분 이내 출차 시 요금면제<br />\n"
                    + "※ 요금정산 후 20분 이내 미출차 시 요금부과<br /><br />\n"
                    + "※ 주차요금 면제 - 공무내방자, 유물기증자, 박물관회 기부회원, 박물관 자원봉사자, 장애인, 국가유공자, 박물관 직원 및 상주기관 임직원<br />\n"
                    + "※ 요금 50% 할인 - 국가유공자의 가족, 1,000cc 이하차량, 환경친화적 자동차, 세미나, 교육생, 박물관시설 대관단체(주최측 차량에 한함)<br />\n"
                    + "※ 기본요금 - 박물관회 특별․일반․후원 회원, 극장 \"용\" 공연관람 시")
                .useFee("무료 : 상설전시관, 어린이박물관, 무료기획전시<br />\n"
                    + "상설전시관 : 관람권 없이 바로 입장<br />\n"
                    + "어린이박물관 : 어린이박물관 앞 안내데스크 <br />\n"
                    + "※ 관람권 발급 시간 : 관람 종료 1시간 전까지<br />\n"
                    + "<br />\n"
                    + "유료 : 유료특별전시<br /> \n"
                    + "※ 관람권 구입 :  기획전시실 앞 매표소(유료 진행 특별전시의 경우는 입장권을 받아 입장)<br />\n"
                    + "※ 관람권 발급 시간 : 관람 종료 1시간 전까지")
                .spendTime("")
                .build());
        }
    }

    public List<PlaceThumbnail> getSimilarDummy(long place_id) {
        List<PlaceThumbnail> placeThumbnailList = new ArrayList<PlaceThumbnail>();
        placeThumbnailList.add(PlaceThumbnail.builder()
            .id(1L)
            .name("숭례문")
            .address("서울 중구")
            .imageURL("http://tong.visitkorea.or.kr/cms/resource/01/1945801_image2_1.jpg")
            .build());
        placeThumbnailList.add(PlaceThumbnail.builder()
            .id(4L)
            .name("국립중앙박물관")
            .address("서울특별시 용산구 서빙고로 137")
            .imageURL("http://tong.visitkorea.or.kr/cms/resource/01/1945801_image2_1.jpg")
            .build());

        return placeThumbnailList;
    }

    public List<PlaceThumbnail> getNearbyDummy(long place_id) {
        List<PlaceThumbnail> placeThumbnailList = new ArrayList<PlaceThumbnail>();
        placeThumbnailList.add(PlaceThumbnail.builder()
            .id(5L)
            .name("숭례문")
            .address("서울 중구")
            .imageURL("http://tong.visitkorea.or.kr/cms/resource/01/1945801_image2_1.jpg")
            .build());
        placeThumbnailList.add(PlaceThumbnail.builder()
            .id(17L)
            .name("국립중앙박물관")
            .address("서울특별시 용산구 서빙고로 137")
            .imageURL("http://tong.visitkorea.or.kr/cms/resource/01/1945801_image2_1.jpg")
            .build());

        return placeThumbnailList;
    }

    public List<PlaceThumbnail> getSearchDummy(String keyword) {
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

        return placeThumbnailList;
    }

    public List<PlaceThumbnail> getRecommendDummy() {
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
        placeThumbnailList.add(PlaceThumbnail.builder()
            .id(3L)
            .name("숭례문")
            .address("서울 중구")
            .imageURL("http://tong.visitkorea.or.kr/cms/resource/01/1945801_image2_1.jpg")
            .build());
        placeThumbnailList.add(PlaceThumbnail.builder()
            .id(5L)
            .name("국립중앙박물관")
            .address("서울특별시 용산구 서빙고로 137")
            .imageURL("http://tong.visitkorea.or.kr/cms/resource/01/1945801_image2_1.jpg")
            .build());

        return placeThumbnailList;
    }

    public List<String> getCompleteWordDummy(String word) {
        List<String> stringList = new ArrayList<String>();
        stringList.add("안녕 승윤");
        stringList.add("나는 시은");
        stringList.add("우리 모두");
        stringList.add("파이팅");
        stringList.add("~!");

        return stringList;
    }
}
