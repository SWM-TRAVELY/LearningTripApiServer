package app.learningtrip.apiserver.sticker.domain;

import app.learningtrip.apiserver.place.domain.Place;
import app.learningtrip.apiserver.user.domain.User;
import javax.persistence.Entity;
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
@IdClass(StickerId.class)
@Table(name="sticker")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Sticker {

    @Id
    @ManyToOne
    @JoinColumn(name = "place_id")
    public Place place;

    @Id
    @ManyToOne
    @JoinColumn(name = "user_id")
    public User user;
}
