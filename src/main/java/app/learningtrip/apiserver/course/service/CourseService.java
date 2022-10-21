package app.learningtrip.apiserver.course.service;

import app.learningtrip.apiserver.course.domain.Course;
import app.learningtrip.apiserver.course.domain.CoursePlace;
import app.learningtrip.apiserver.course.dto.request.CoursePlaceRequest;
import app.learningtrip.apiserver.course.dto.request.CourseRequest;
import app.learningtrip.apiserver.course.dto.response.CoursePlaceResponse;
import app.learningtrip.apiserver.course.dto.response.CourseResponse;
import app.learningtrip.apiserver.course.dto.response.CourseThumbnail;
import app.learningtrip.apiserver.course.repository.CoursePlaceRepository;
import app.learningtrip.apiserver.course.repository.CourseRepository;
import app.learningtrip.apiserver.place.domain.Place;
import app.learningtrip.apiserver.place.repository.PlaceRepository;
import app.learningtrip.apiserver.place.service.PlaceService;
import app.learningtrip.apiserver.user.domain.User;
import app.learningtrip.apiserver.user.repository.UserRepository;
import java.rmi.NoSuchObjectException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class CourseService {

    private final CourseRepository courseRepository;
    private final CoursePlaceRepository coursePlaceRepository;
    private final PlaceService placeService;
    private final PlaceRepository placeRepository;
    private final UserRepository userRepository;

    /**
     * 코스 정보 조회
     */
    public CourseResponse getInfo(Long id) {

        // course table 조회
        Optional<Course> course = courseRepository.findById(id);
        List<CoursePlace> coursePlaceList = coursePlaceRepository.findAllByCourseIdOrderByDayAscSequenceAsc(id);

        if (course.isPresent() == false) {
            throw new RuntimeException(Long.toString(id) + "번의 코스 정보를 찾을 수 없습니다.");
        }

        List<CoursePlaceResponse> coursePlaceResponses = new ArrayList<CoursePlaceResponse>();
        Double latitude = null;
        Double longitude = null;
        for (CoursePlace coursePlace : coursePlaceList) {
            Double distance = Double.valueOf(0);
            System.out.print("위도 경도 : ");
            System.out.print(latitude);
            System.out.print(longitude);
            if (latitude != null & longitude != null) {
                distance = getDistance(latitude, longitude, coursePlace.place.getLatitude(), coursePlace.place.getLongitude(), "kilometer");
            }
            latitude = coursePlace.place.getLatitude();
            longitude = coursePlace.place.getLongitude();
            coursePlaceResponses.add(CoursePlaceResponse.toResponse(coursePlace, distance));
        }

        CourseResponse courseResponse = CourseResponse.builder()
            .id(course.get().getId())
            .name(course.get().getName())
            .placeList(coursePlaceResponses)
            .build();

        return courseResponse;
    }

    private static double getDistance(double lat1, double lon1, double lat2, double lon2, String unit) {
        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));

        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;

        if (unit == "kilometer") {
            dist = dist * 1.609344;
        } else if(unit == "meter"){
            dist = dist * 1609.344;
        }

        return (dist);
    }

    // This function converts decimal degrees to radians
    private static double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    // This function converts radians to decimal degrees
    private static double rad2deg(double rad) {
        return (rad * 180 / Math.PI);
    }

//    public Model getInfoNotDay(Long id, Model model) {
//
//        // course table 조회
//        Optional<Course> course = courseRepository.findById(id);
//        List<CoursePlace> coursePlaceList = coursePlaceRepository.findAllByCourseIdOrderByDayAscSequenceAsc(id);
//
//        if (course.isPresent() == false) {
//            throw new RuntimeException(Long.toString(id) + "번의 코스 정보를 찾을 수 없습니다.");
//        }
//
//        List<CoursePlaceResponse> placeList = new ArrayList<CoursePlaceResponse>();
//        for (CoursePlace coursePlace : coursePlaceList) {
//            placeList.add(CoursePlaceResponse.toResponse(coursePlace.getPlace()));
//        }
//
//
//        model.addAttribute("id", course.get().getId());
//        model.addAttribute("name", course.get().getName());
//        model.addAttribute("placeList", placeList);
//
//        return model;
//    }

    /**
     * 코스 리스트 조회
     */
    public List<CourseThumbnail> getList(User user) {

        List<CourseThumbnail> courseThumbnailList = new ArrayList<CourseThumbnail>();

        // course table 조회
        List<Course> courseList = courseRepository.findAllByUserId(user.getId());
        if (courseList.size() == 0) {
            return courseThumbnailList;
        }

        // course_place table 조회
        for (Course course : courseList) {
            List<CoursePlace> coursePlaceList = coursePlaceRepository.findAllByCourseIdOrderByDayAscSequenceAsc(course.getId());

            List<Place> placeList = new ArrayList<Place>();
            for (CoursePlace coursePlace : coursePlaceList) {
                placeList.add(coursePlace.getPlace());
            }

            courseThumbnailList.add(CourseThumbnail.toThumbnail(course, placeList));
        }

        return courseThumbnailList;
    }

    /**
     * 코스 생성
     */
    public void setCourse(CourseRequest courseRequest, User user) {

        // Course 탐색, 없으면 생성
        Optional<Course> course = courseRepository.findById(courseRequest.getId());

        if (!course.isPresent()) {
            course = Optional.ofNullable(courseRepository.save(Course.builder()
                .name(courseRequest.getName())
                .user(user)
                .build()));
        }
        else {
            course.get().setName(courseRequest.getName());
            courseRepository.save(course.get());
        }

        // Course-Place에서 현재 course 데이터 삭제
        coursePlaceRepository.deleteAllByCourseId(course.get().getId());

        // Course-Place 생성
        List<CoursePlaceRequest> placeList = courseRequest.getPlaceList();
        for (CoursePlaceRequest coursePlaceRequest : placeList) {
            Optional<Place> place = placeRepository.findById(coursePlaceRequest.getId());
            place.orElseThrow(() -> new NoSuchElementException("존재하지 않은 Place입니다."));

            coursePlaceRepository.save(CoursePlace.builder()
                .day(coursePlaceRequest.getDay())
                .sequence(coursePlaceRequest.getSequence())
                .course(course.get())
                .place(place.get())
                .build());
        }
    }

    /**
     * 코스 삭제
     */
    public void delete(CourseRequest courseRequest, User user) {
        // Course 탐색, 없으면 생성
        Optional<Course> course = courseRepository.findById(courseRequest.getId());
        course.orElseThrow(() -> new NoSuchElementException("존재하지 않은 Place입니다."));

        // Course-Place에서 course 데이터 삭제
        coursePlaceRepository.deleteAllByCourseId(course.get().getId());

        // Course에서 삭제
        courseRepository.delete(course.get());
    }


    /**
     * 추천 코스
     */
    public List<CourseThumbnail> getRecommend() throws NoSuchObjectException {
        int countOfCourse = courseRepository.countAllBy();
        if (countOfCourse < 4) {
            return getRecommendDummy();
        }

        List<CourseThumbnail> courseThumbnailList = new ArrayList<CourseThumbnail>();

        for (int i = 0; i < 4; i++) {
            int index = (int)(Math.random() * countOfCourse) + 1;

            Optional<Course> course = courseRepository.findById(Long.valueOf(index));
            course.orElseThrow(() -> new NoSuchObjectException("없는 경로를 조회했습니다."));

            List<CoursePlace> coursePlaceList = coursePlaceRepository.findAllByCourseIdOrderByDayAscSequenceAsc(Long.valueOf(index));
            List<Place> placeList = new ArrayList<Place>();
            for (CoursePlace coursePlace : coursePlaceList) {
                placeList.add(coursePlace.getPlace());
            }

            CourseThumbnail courseThumbnail = CourseThumbnail.toThumbnail(course.get(), placeList);
            courseThumbnailList.add(courseThumbnail);
        }
        return courseThumbnailList;
    }

    /**
     * 추천 코스 생성
     */
    public void makePlaces(String region, int days, Course course) {
        int placeCountOfDay = 2;

        List<Place> placeList =  placeService.findSeoulTextbookPlace();

        List<Integer> duplicationCheckList = new ArrayList<Integer>();
        for (int day = 1; day <= days; day++) {
            for (int placeCount = 0; placeCount < placeCountOfDay; placeCount++) {
                int index = (int)(Math.random() * placeList.size());

                // 중복 체크
                int flag = 0;
                for (int listLength = 0; listLength < duplicationCheckList.size(); listLength++) {
                    if (index == duplicationCheckList.get(listLength)) {
                        flag = 1;
                    }
                }
                if (flag == 1) {
                    placeCount--;
                    continue;
                }

                duplicationCheckList.add(index);

                coursePlaceRepository.save(CoursePlace.builder()
                    .day(day)
                    .sequence(placeCount+1)
                    .course(course)
                    .place(placeList.get(index))
                    .build());
            }
        }
    }

    public void makeCourse(String region, int days, int num) {
        String name;
        if (days == 1) {
            name = "당일치기 "+region+" 여행 "+Integer.toString(num+1);
        } else {
            name = Integer.toString(days-1)+"박 "+Integer.toString(days)+"일 "+region+" 여행 "+Integer.toString(num+1);
        }

        Optional<User> user = userRepository.findById(1L);

        Course course = courseRepository.save(Course.builder()
            .name(name)
            .user(user.get())
            .build());

        makePlaces(region, days, course);
    }

    public void makeCourses() {
        List<String> regions = new ArrayList<String>();
        regions.add("서울");
        //regions.add("경주");

        List<CourseThumbnail> courseThumbnailList = new ArrayList<CourseThumbnail>();
        for (String region : regions) {
            for (int days = 1; days < 5; days ++) {
                for (int num = 0; num < 30; num++) {
                    makeCourse(region, days, num);
                }
            }
        }
    }

    /**
     * Dummy Data 조회
     */
//    public CourseResponse getInfoDummy(Long id) {
//        List<CoursePlaceResponse> placeList1 = new ArrayList<CoursePlaceResponse>();
//
//        placeList1.add(CoursePlaceResponse.builder()
//            .id(1L)
//            .name("숭례문")
//            .description(
//                "숭례문(崇禮門)은 조선 태조 5년(1396)에 최초로 축조되었고 1398년 2월 중건되었다. 이 문은 조선시대 한성 도성의 정문으로  4대문 가운데 남쪽에 위치하므로, 남대문으로도 불린다. 1448년에도 크게 고쳐지었다. "
//                    + "이후 임진왜란과 병자호란 때에도 남대문은 피해를 입지 않았다.처음 만들어졌을 때는 양측에 성벽이 연결되어 있었지만 1908년 도로를 내기 위하여 헐어 내고 성문만 섬처럼 따로 떨어져 있었으나, 2006년 복원 공사를 마치고 지금과 같은 모습을 하게 되었다. "
//                    + "1962년 문화재보호법에 의하여 보물에서 국보로 지정되었다.<br><br>건물의 평면은 아래.위층이 모두 5칸, 측면 2칸이며,  건물 내부의 아래층 바닥은 홍예의 윗면인 중앙칸만이 우물마루일 뿐, 다른 칸은 흙바닥으로 되어있고 위층은 널마루이다. "
//                    + "편액의 필자에 관하여는 여러가지 설이 있으나, &lt;지봉유설&gt;에는 양녕대군이 쓴 것이라고 기록되어 있다. 다른 문의 편액은 가로로 쓰여 있으나 숭례문이 세로로 쓰여 있는것은 숭례(崇禮)의 두 글자가 불꽃을 의미하여, 경복궁을 마주보는 관악산의 불기운을 누르게 하기 위해서라고 한다."
//                    + "<br><br>현존하는 성문 건물로는 우리나라에서 가장 규모가 큰 남대문은 전형적인 다포(多包)양식의 건물로 견실한 목조건축물의 수범을 보이고 있는 한국 건축사상 중요한 건물의 하나이다. "
//                    + "1997년 초 서울시에서 이 문의 경관을 더욱 돋보이게 하기 위하여 조명설비를 새로이 한 바 있다.서울 중구는 2005년 10월부터 통로 보수공사와 홍예문 입구 등 5곳에 대한 지표조사를 실시, 조선 세종때의 것으로 추정되는 아랫부분 석축 기단과 지대석(맨 아래 기초석), 박석(바닥에 까는 돌), 문지도리(문을 다는 돌 구조물) 등을 발굴했다."
//                    + "<br><br>이는 1907년께 남대문을 관통하던 전차선로를 내면서 문 주위로 흙을 1미터 가량 쌓아올려 아래쪽 기단과 박석들이 완전히 묻힌 것으로 보인다고 밝혔다. 따라서 중구는 시민들이 지반보다 1.6ｍ 아래에 있는 이들 구조물을 볼 수 있도록 중앙통로 시굴 부분을 그대로 남겨둔 채 관람시설을 설치했다. "
//                    + "숭례문의 중앙통로인 홍예문을 따라 숭례문을 둘러볼 수 있다. 그러나 숭례문은 2008년 2월 10일 화재로 인하여 전소되었고, 5년 3개월에 걸친 복구 사업을 완료하고 2013년 개방되었다. 2013년 5월 1일에는 숭례문 복구를 고하는 '고유제(告由祭)'가 치러졌다."
//                    + "숭례문 복원에는 충남 태안의 안면송이 사용되었다. 안면송은 고려시대부터 궁궐이나 선박용으로 사용되어 왔으며, 이번 숭례문의 석가래, 기둥, 지붕 등에 사용되었다.")
//            .imageURL("http://tong.visitkorea.or.kr/cms/resource/01/1945801_image2_1.jpg")
//            .address("서울특별시 중구 세종대로 40")
//            .build());
//
//        placeList1.add(CoursePlaceResponse.builder()
//            .id(2L)
//            .name("국립중앙박물관")
//            .description(
//                "국립중앙박물관은 42만 점의 소장유물을 소장하고 있으며, 고고, 역사, 미술, 기증, 아시아 관련 문화재를 전시하는 상설 전시실과 다양한 전시가 가능하도록 가변성 있게 구성된 기획 전시실, 체험과 참여 학습을 통해 전시를 이해하도록 설계된 어린이 박물관, 박물관 야외정원을 이용하여 석탑 등 다양한 석조유물을 전시한 야외전시실로 이뤄진다. "
//                    + "국립중앙박물관은 국내·외 전시활동 외에도 유물의 수집과 보존, 조사연구, 사회교육활동, 학술자료발간, 국제문화교류활동, 각종 공연 등의 기회를 제공하는 복합문화공간으로서 교육적 측면 뿐 아니라 친환경 녹색공간과 휴게시설 및 양질의 문화 프로그램도 함께 마련되어 있어  남녀노소를 불문하고 언제든 찾아가고 싶은 새로운 도심 속 명소의  역할을 하고 있다. "
//                    + "국립중앙박물관은 정원의 폭포와 아름다운 전경으로도 유명하며, 산책을 즐기기에도 그만이다. 전시관 내에 카페테리아와 휴게공간, 아트숍, 식당가, 편의점 등도 편리하게 이용할 수 있다.<br><br><strong>[나주 서성문 안 석등(보물/1963.01.21)] </strong><br>본래 전라남도 나주읍 서문 안에 있던 것을 1929년에 경복궁으로 옮겨놓은 것이다. "
//                    + "현재는 국립중앙박물관에 옮겨져 있다. 석등은 불을 밝혀두는 화사석(火舍石)을 중심으로, 아래는 3단을 이루는 받침을 두고 위로는 지붕돌과 머리장식을 얹었다. 네모난 모양의 널찍한 바닥돌 위에 세워져 있으며, 아래받침돌은 8각이고 연꽃문양이 새겨져 있다. 기둥모양의 중간받침은 8면으로 각 면마다 테를 둘러 공간을 만들고 그 중심 안에 한 줄씩의 문장을 새겼다. "
//                    + "윗받침돌은 8각면에 돌아가며 연꽃무늬를 조각했고, 화사석은 새로 만들어 놓은 것으로 창이 4개다. 지붕돌은 매우 장식적으로 8개 면마다 처마 끝에 짧은 막을 드리운 것처럼 세로줄무늬가 있고, 그 위로 막 피어오르는 형상의 꽃장식이 두툼하게 달려있다. 지붕돌 위로는 마치 지붕을 축소해 놓은 듯한 돌이 올려져 있고, 맨 꼭대기에 올려진 연꽃봉오리 모양의 돌은 석등을 옮겨 세울 때 새로 만든 것이다. "
//                    + "받침에 새겨져 있는 기록을 통해 고려 선종 10년(1093)에 석등을 세웠음을 알 수 있다.각 부분의 조각이 둔중한 편이나 지붕돌의 형태가 장식적인 공예기법을 보이고 있는 특이한 고려시대 작품으로, 통일신라시대 8각형 석등의 양식을 이어받으면서도 그 구조와 조각이 우수하다.<br><br><strong>[원주 흥법사지 염거화상탑(국보/1962.12.20)]</strong><br>"
//                    + "통일신라 말의 승려 염거화상의 사리탑이다. 염거화상(?∼844)은 도의선사의 제자로, 선(禪)에 대한 이해가 거의 없었던 당시 주로 설악산 억성사에 머물며 선을 널리 알리는데 힘썼다. 체징에게 그 맥을 전하여 터전을 마련한 뒤 문성왕 6년(844)에 입적하였다. "
//                    + "이 탑은 원래 강원도 흥법사터에 서 있었다 하나, 이에 대한 확실한 근거가 없기 때문에 탑이름 앞에 ‘전(傳)’자를 붙이게 되었고, 원래 위치에서 서울로 옮겨진 후에도 탑골공원 등 여러 곳에 옮겨졌다가 경복궁에 세워졌으나 현재 국립중앙박물관에 옮겨져 있다.탑은 아래위 각 부분이 8각의 평면을 기본으로 삼고 있다. "
//                    + "기단(基壇)은 밑돌·가운데돌·윗돌의 세 부분으로 이루어져 있으며, 각 면마다 소박한 조각이 멋스럽게 펼쳐져 있다. 밑돌에는 사자를 도드라지게 새겼고, 가운데돌에는 움푹 새긴 안상(眼象)안에 향로를 새겨 두었다.<br><br>2단으로 마련된 윗돌은 아래단에는 연꽃을 두 줄로 돌려 우아함을 살리고 윗단에는 둥그스름한 안상(眼象) 안에 여러 조각을 두어 장식하였다. "
//                    + "사리를 모셔둔 탑신(塔身)의 몸돌은 면마다 문짝모양, 4천왕상(四天王像)을 번갈아가며 배치하였는데, 입체감을 잘 살려 사실적으로 표현하였다. 지붕돌은 당시의 목조건축양식을 특히 잘 따르고 있어서 경사면에 깊게 패인 기왓골, 기와의 끝마다 새겨진 막새기와 모양, 밑면의 서까래표현 등은 거의 실제 건물의 기와지붕을 보고 있는 듯하다. 꼭대기에 있는 머리장식은 탑을 옮기기 전까지 남아 있었으나, 지금은 모두 없어졌다. "
//                    + "탑을 옮겨 세울 때 그 안에서 금동탑지(金銅塔誌)가 발견되었는데, 이를 통해 통일신라 문성왕 6년(844)에 이 탑을 세웠음을 알게 되었다. 사리탑 중에서는 가장 오래된 것으로, 규모는 그리 크지 않으나 단아한 기품과 깨끗한 솜씨가 잘 어우러져 있다. 이후 대부분의 사리탑이 이 양식을 따르고 있어 그 최초의 의의를 지니는 작품이다.")
//            .imageURL("http://tong.visitkorea.or.kr/cms/resource/72/2658572_image2_1.jpg")
//            .address("서울특별시 용산구 서빙고로 137")
//            .build());
//
//        List<CourseDay> dayCourseList = new ArrayList<CourseDay>();
//        dayCourseList.add(CourseDay.builder()
//            .day(1)
//            .placeList(placeList1)
//            .build());
//
//        CourseResponse courseResponse = CourseResponse.builder()
//            .id(1)
//            .name("시은이의 신나는 경주 여행")
//            .dayList(dayCourseList)
//            .build();
//
//        return courseResponse;
//    }

    public List<CourseThumbnail> getListDummy() {
        List<CourseThumbnail> courseThumbnailList = new ArrayList<CourseThumbnail>();
        courseThumbnailList.add(CourseThumbnail.builder()
            .id(1L)
            .name("시은이의 신나는 경주여행 코스!~1")
            .imageURL("https://www.google.com/imgres?imgurl=https%3A%2F%2Fwww.gyeongju.go.kr%2Fupload%2Fcontent%2Fthumb%2F20191221%2F61FBB0185F5E46ADBEC117779F2F8150.jpg&imgrefurl=https%3A%2F%2Fwww.gyeongju.go.kr%2Ftour%2Fpage.do%3Fmnu_uid%3D2695%26con_uid%3D246%26cmd%3D2&tbnid=BAwAYSr4z2Lg9M&vet=12ahUKEwjo7t-ihZv6AhVK_JQKHdJJAK0QMygCegUIARDgAQ..i&docid=aX6WYCeZiJBRFM&w=1600&h=1067&q=%EB%B6%88%EA%B5%AD%EC%82%AC&ved=2ahUKEwjo7t-ihZv6AhVK_JQKHdJJAK0QMygCegUIARDgAQ")
            .place1("불국사")
            .place2("첨성대")
            .place3("석가탑")
            .build());
        courseThumbnailList.add(CourseThumbnail.builder()
            .id(2L)
            .name("시은이의 신나는 경주여행 코스!~2")
            .imageURL("https://www.google.com/imgres?imgurl=https%3A%2F%2Fwww.gyeongju.go.kr%2Fupload%2Fcontent%2Fthumb%2F20191221%2F61FBB0185F5E46ADBEC117779F2F8150.jpg&imgrefurl=https%3A%2F%2Fwww.gyeongju.go.kr%2Ftour%2Fpage.do%3Fmnu_uid%3D2695%26con_uid%3D246%26cmd%3D2&tbnid=BAwAYSr4z2Lg9M&vet=12ahUKEwjo7t-ihZv6AhVK_JQKHdJJAK0QMygCegUIARDgAQ..i&docid=aX6WYCeZiJBRFM&w=1600&h=1067&q=%EB%B6%88%EA%B5%AD%EC%82%AC&ved=2ahUKEwjo7t-ihZv6AhVK_JQKHdJJAK0QMygCegUIARDgAQ")
            .place1("불국사")
            .place2("첨성대")
            .place3(null)
            .build());
        courseThumbnailList.add(CourseThumbnail.builder()
            .id(3L)
            .name("시은이의 신나는 경주여행 코스!~3")
            .imageURL(null)
            .place1(null)
            .place2(null)
            .place3(null)
            .build());
        return courseThumbnailList;
    }

    public List<CourseThumbnail> getRecommendDummy() {
        List<CourseThumbnail> courseThumbnailList = new ArrayList<CourseThumbnail>();
        courseThumbnailList.add(CourseThumbnail.builder()
            .id(1L)
            .name("시은이의 신나는 경주여행 코스!~1")
            .imageURL("https://www.google.com/imgres?imgurl=https%3A%2F%2Fwww.gyeongju.go.kr%2Fupload%2Fcontent%2Fthumb%2F20191221%2F61FBB0185F5E46ADBEC117779F2F8150.jpg&imgrefurl=https%3A%2F%2Fwww.gyeongju.go.kr%2Ftour%2Fpage.do%3Fmnu_uid%3D2695%26con_uid%3D246%26cmd%3D2&tbnid=BAwAYSr4z2Lg9M&vet=12ahUKEwjo7t-ihZv6AhVK_JQKHdJJAK0QMygCegUIARDgAQ..i&docid=aX6WYCeZiJBRFM&w=1600&h=1067&q=%EB%B6%88%EA%B5%AD%EC%82%AC&ved=2ahUKEwjo7t-ihZv6AhVK_JQKHdJJAK0QMygCegUIARDgAQ")
            .place1("불국사")
            .place2("첨성대")
            .place3("석가탑")
            .build());
        courseThumbnailList.add(CourseThumbnail.builder()
            .id(2L)
            .name("시은이의 신나는 경주여행 코스!~2")
            .imageURL("https://www.google.com/imgres?imgurl=https%3A%2F%2Fwww.gyeongju.go.kr%2Fupload%2Fcontent%2Fthumb%2F20191221%2F61FBB0185F5E46ADBEC117779F2F8150.jpg&imgrefurl=https%3A%2F%2Fwww.gyeongju.go.kr%2Ftour%2Fpage.do%3Fmnu_uid%3D2695%26con_uid%3D246%26cmd%3D2&tbnid=BAwAYSr4z2Lg9M&vet=12ahUKEwjo7t-ihZv6AhVK_JQKHdJJAK0QMygCegUIARDgAQ..i&docid=aX6WYCeZiJBRFM&w=1600&h=1067&q=%EB%B6%88%EA%B5%AD%EC%82%AC&ved=2ahUKEwjo7t-ihZv6AhVK_JQKHdJJAK0QMygCegUIARDgAQ")
            .place1("불국사")
            .place2("첨성대")
            .place3("")
            .build());
        courseThumbnailList.add(CourseThumbnail.builder()
            .id(3L)
            .name("시은이의 신나는 경주여행 코스!~3")
            .imageURL("")
            .place1("")
            .place2("")
            .place3("")
            .build());
        return courseThumbnailList;
    }
}
