package app.learningtrip.apiserver.heritage.repository;

import static org.junit.Assert.*;

import app.learningtrip.apiserver.heritage.domain.Heritage;
import app.learningtrip.apiserver.place.domain.Place;
import app.learningtrip.apiserver.place.repository.PlaceRepository;
import java.util.Optional;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringRunner.class)
@SpringBootTest
public class HeritageRepositoryTest {

    @Autowired PlaceRepository placeRepository;
    @Autowired HeritageRepository heritageRepository;

    @Test
    @Transactional
    public void testHeritage() throws Exception {
        // given
        Place place = placeRepository.save(Place.builder()
            .type(12)
            .name("place")
            .address("address")
            .build());

        Heritage heritage = heritageRepository.save(Heritage.builder()
            .name("heritage")
            .address("address")
            .place(place)
            .build());

        // when
        Optional<Heritage> findHeritage = heritageRepository.findById(heritage.getId());

        // then
        Assertions.assertThat(findHeritage.get().getName()).isEqualTo(heritage.getName());
        Assertions.assertThat(findHeritage.get().getType()).isEqualTo(heritage.getType());
        Assertions.assertThat(findHeritage.get().getAddress()).isEqualTo(heritage.getAddress());

    }
}