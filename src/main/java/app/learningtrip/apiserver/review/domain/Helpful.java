package app.learningtrip.apiserver.review.domain;

import app.learningtrip.apiserver.place.domain.Place;
import app.learningtrip.apiserver.user.domain.User;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "helpful")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Helpful {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long id;

    private boolean helpful;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "review_id")
    public Review review;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    public User user;

}
