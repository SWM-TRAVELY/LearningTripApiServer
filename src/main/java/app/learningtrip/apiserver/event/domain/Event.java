package app.learningtrip.apiserver.event.domain;

import javax.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Entity
@Builder(builderMethodName = "EventBuilder")
@Getter @Setter
@Table
public class Event {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(unique = true, nullable = false)
  private int id;

  private String imgURL;

  public static EventBuilder builer() {
      return EventBuilder();
  }
}
