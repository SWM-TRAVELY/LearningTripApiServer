package app.learningtrip.apiserver.place.service;


import app.learningtrip.apiserver.place.dto.response.PlaceInfoResponse;
import app.learningtrip.apiserver.place.domain.PlaceTempEntity;
import app.learningtrip.apiserver.place.repository.PlaceTempRepository;
import org.springframework.stereotype.Service;

@Service
public class PlaceInfoService {
    private final PlaceTempRepository placeTempRepository;

    public PlaceInfoService(PlaceTempRepository placeTempRepository) {
        this.placeTempRepository = placeTempRepository;
    }

    public PlaceInfoResponse getPlaceInfo(long placeId){
        PlaceTempEntity placeTempEntity = placeTempRepository.findById(placeId).orElseThrow(null);
        return placeTempEntity.toPlaceInfoResponse();
    }
}
