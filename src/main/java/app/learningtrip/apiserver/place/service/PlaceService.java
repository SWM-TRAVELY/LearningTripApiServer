package app.learningtrip.apiserver.place.service;

import app.learningtrip.apiserver.place.domain.Place;
import app.learningtrip.apiserver.place.repository.PlaceRepository;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class PlaceService {

    private final PlaceRepository placeRepository;

    public PlaceService(PlaceRepository placeRepository) {
        this.placeRepository = placeRepository;
    }

    /**
     * place 조회
     */
    public Optional<Place> findOne(long id) {
        return placeRepository.findById(id);
    }

}
