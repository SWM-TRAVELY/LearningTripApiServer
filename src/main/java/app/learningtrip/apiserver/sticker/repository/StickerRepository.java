package app.learningtrip.apiserver.sticker.repository;

import app.learningtrip.apiserver.sticker.domain.Sticker;
import app.learningtrip.apiserver.sticker.domain.StickerId;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StickerRepository extends JpaRepository<Sticker, StickerId> {

    Optional<Sticker> findByPlaceIdAndUserId(Long place_id, Long user_id);

    List<Sticker> findAllByUserId(Long user_id);
}
