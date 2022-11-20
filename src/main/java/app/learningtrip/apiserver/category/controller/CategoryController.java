package app.learningtrip.apiserver.category.controller;

import app.learningtrip.apiserver.category.dto.CourseOptionsResponse;
import app.learningtrip.apiserver.category.service.CategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = {"카테고리 API"})
@RestController
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;


    @GetMapping("/course/options")
    @ApiOperation(value = "코스 옵션 가져오기", notes = "코스 옵션(학년, 지역)을 가져온다")
    public ResponseEntity<CourseOptionsResponse> getCourseOptions(){
        return ResponseEntity.ok().body(categoryService.getCourseOptions());
    }

    @GetMapping("/course/options/keyword/{keyword}")
    @ApiOperation(value = "코스 옵션 키워드 조회", notes = "코스 옵션 중 키워드 검색")
    public ResponseEntity<List<String>> getKeywordOfCourseOptions(@PathVariable(name = "keyword") String keyword){
        return ResponseEntity.ok().body(categoryService.getKeywordOfCourseOptions(keyword));
    }

    @GetMapping("/category")
    @ApiOperation(value = "카테고리 목록 조회", notes = "카테고리(지역, 학년) 목록을 조회한다.")
    public ResponseEntity<CourseOptionsResponse> getCategory(){
        return ResponseEntity.ok().body(categoryService.getCourseOptions());
    }
}
