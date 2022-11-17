package app.learningtrip.apiserver.category.service;

import app.learningtrip.apiserver.category.domain.Grade;
import app.learningtrip.apiserver.category.domain.Province;
import app.learningtrip.apiserver.category.dto.CourseOptionsResponse;
import app.learningtrip.apiserver.category.repository.GradeRepository;
import app.learningtrip.apiserver.category.repository.ProvinceRepository;
import app.learningtrip.apiserver.keyword.repository.KeywordRepository;
import app.learningtrip.apiserver.search.service.SearchServiceImpl;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class CategoryService {

    private final GradeRepository gradeRepository;

    private final ProvinceRepository provinceRepository;

    private final KeywordRepository keywordRepository;

    private final SearchServiceImpl searchService;

    /**
     * 코스 옵션 (학년, 지역) 조회
     * @return 코스 옵션 (학년, 지역)
     */
    public CourseOptionsResponse getCourseOptions(){
        CourseOptionsResponse response = new CourseOptionsResponse();

        List<Grade> gradeList = gradeRepository.findAll(Sort.by(Direction.ASC, "id"));
        List<Province> provinceList = provinceRepository.findAll(Sort.by(Direction.ASC, "id"));

        response.gradeToOptions(gradeList);
        response.provinceToOptions(provinceList);

        return response;
    }

    /**
     * 코스 옵션 키워드 검색
     * @param keyword 키워드
     * @return 키워드 리스트
     */
    public List<String> getKeywordOfCourseOptions(String keyword){
        return searchService.sortSearchList(keywordRepository.findKeywordsByNameLike(keyword), keyword);
    }
}
