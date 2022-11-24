package app.learningtrip.apiserver.course.domain;

import app.learningtrip.apiserver.place.domain.Place;
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
@Table(name = "course_place_recommend")
@AllArgsConstructor @NoArgsConstructor
@Getter @Setter @Builder
public class CoursePlaceRecommend {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    Long id;

    Integer day;

    Integer sequence;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id")
    public CourseRecommend course;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "place_id")
    public Place place;

    public CoursePlaceRecommend(Integer day, Integer sequence, CourseRecommend course,
        Place place) {
        this.day = day;
        this.sequence = sequence;
        this.course = course;
        this.place = place;
    }
}
