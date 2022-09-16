package app.learningtrip.apiserver.place.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter @Setter
@SuperBuilder
public class Place_Detail_Tour extends Place {

  private String expAge;
  private String expGuide;

  private boolean heritageCul;
  private boolean heritageNat;
  private boolean heritageRec;

}
