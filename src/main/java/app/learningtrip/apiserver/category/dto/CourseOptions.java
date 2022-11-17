package app.learningtrip.apiserver.category.dto;

import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter @Getter
@NoArgsConstructor
public class CourseOptions {
  private String group = "";

  private List<String> options = new ArrayList<>();

  public CourseOptions(CourseOptions courseOptions) {
    this.group = courseOptions.getGroup();
    this.options = courseOptions.getOptions();
  }

  public CourseOptions newInstance(){
    return new CourseOptions(this);
  }

  public void clearOptions(){
    this.options = new ArrayList<>();
  }

  public void addOption(String option){
    this.options.add(option);
  }
}
