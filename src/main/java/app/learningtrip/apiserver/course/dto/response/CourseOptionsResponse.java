package app.learningtrip.apiserver.course.dto.response;

import app.learningtrip.apiserver.course.domain.Grade;
import app.learningtrip.apiserver.course.domain.Province;
import app.learningtrip.apiserver.course.dto.CourseOptions;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor @Getter
public class CourseOptionsResponse {
  private final List<CourseOptions> grade = new ArrayList<>();

  private final List<CourseOptions> province = new ArrayList<>();

  public void gradeToOptions(List<Grade> gradeList){
    CourseOptions gradeOptions = new CourseOptions();

    gradeList.forEach(grade -> {
      if(!gradeOptions.getGroup().equals(grade.getSchool())){
        if(!gradeOptions.getOptions().isEmpty()){
          this.grade.add(gradeOptions.newInstance());
        }
        gradeOptions.clearOptions();
        gradeOptions.setGroup(grade.getSchool());
      }

      gradeOptions.addOption(grade.getGrade());
    });
    this.grade.add(gradeOptions.newInstance());
  }

  public void provinceToOptions(List<Province> provinceList){
    CourseOptions provinceOptions = new CourseOptions();

    provinceList.forEach(province -> {
      if(!provinceOptions.getGroup().equals(province.getProvince())){
        if(!provinceOptions.getOptions().isEmpty()){
          this.province.add(provinceOptions.newInstance());
        }
        provinceOptions.clearOptions();
        provinceOptions.setGroup(province.getProvince());
      }
      provinceOptions.addOption(province.getCity());
    });
    this.province.add(provinceOptions.newInstance());
  }
}
