package app.learningtrip.apiserver.place.repository;

import app.learningtrip.apiserver.place.domain.Place;
import java.util.Optional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PlaceRepositoryTest {

    @Autowired PlaceRepository placeRepository;

    @Test
    @Transactional
    public void testPlace() throws Exception {
        // given
        Place place = placeRepository.save(Place.builder()
            .type(12)
            .name("place")
            .address("address")
            .build());

        // when
        Optional<Place> findPlace = placeRepository.findById(place.getId());

        // then
        Assertions.assertThat(findPlace.get().getName()).isEqualTo(place.getName());
        Assertions.assertThat(findPlace.get().getType()).isEqualTo(place.getType());
        Assertions.assertThat(findPlace.get().getAddress()).isEqualTo(place.getAddress());

    }
}