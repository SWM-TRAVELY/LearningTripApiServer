package app.learningtrip.apiserver.place.domain;

import app.learningtrip.apiserver.sticker.domain.StickerId;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Table(name = "similar")
@AllArgsConstructor @NoArgsConstructor
@Getter @Setter @Builder
public class Similar {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long id;

    private String similar;

    @OneToOne
    @JoinColumn(name = "place_id")
    private Place place;
}