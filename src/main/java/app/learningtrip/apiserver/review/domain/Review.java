package app.learningtrip.apiserver.review.domain;

import app.learningtrip.apiserver.place.domain.Place;
import app.learningtrip.apiserver.user.domain.User;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "review")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long id;

    private String content;

    private Date date;

    private Integer rating;

    private Integer imageCount;

    @ManyToOne
    @JoinColumn(name = "place_id")
    public Place place;

    @ManyToOne
    @JoinColumn(name = "user_id")
    public User user;
}
