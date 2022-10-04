package app.learningtrip.apiserver.keyword.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity @Table
@AllArgsConstructor @NoArgsConstructor
@Getter @Setter @Builder
public class Keyword {

    @Id
    @Column(unique = true, nullable = false)
    private String name;

    private String imageURL;

    private Integer count;
}