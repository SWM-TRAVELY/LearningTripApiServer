package app.learningtrip.apiserver.sticker.service;

import app.learningtrip.apiserver.place.domain.Place;
import app.learningtrip.apiserver.place.repository.PlaceRepository;
import app.learningtrip.apiserver.sticker.domain.Sticker;
import app.learningtrip.apiserver.sticker.repository.StickerRepository;
import app.learningtrip.apiserver.user.domain.User;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class StickerService {

    private final StickerRepository stickerRepository;
    private final PlaceRepository placeRepository;

    /**
     * 스티커 수집
     */
    public void insert(Long place_id, User user) {
        Optional<Place> place = placeRepository.findById(place_id);
        place.orElseThrow(() -> new NoSuchElementException("존재하지 않은 Place입니다."));

        // 스티커 수집 가능 거리인지 파익 필요

        // 중복 조회
        stickerRepository.findByPlaceIdAndUserId(place_id, user.getId())
            .ifPresent(m -> {
                throw new IllegalStateException("수집된 스티커입니다.");
            });

        // 스티커 저장
        stickerRepository.save(Sticker.builder()
            .place(place.get())
            .user(user)
            .build());
    }

    /**
     * 스티커 조회
     */
    public List<String> get(User user) {
        List<Sticker> stickerList = stickerRepository.findAllByUserId(user.getId());

        List<String> placeImageList = new ArrayList<String>();
        for (Sticker sticker : stickerList) {
            placeImageList.add(sticker.place.getImageURL1());
        }

        return placeImageList;
    }
}
