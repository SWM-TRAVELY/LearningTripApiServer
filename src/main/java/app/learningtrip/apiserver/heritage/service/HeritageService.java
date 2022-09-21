package app.learningtrip.apiserver.heritage.service;

import app.learningtrip.apiserver.heritage.domain.Heritage;
import app.learningtrip.apiserver.heritage.repository.HeritageRepository;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class HeritageService {

    private final HeritageRepository heritageRepository;

    public HeritageService(HeritageRepository heritageRepository) {
        this.heritageRepository = heritageRepository;
    }

    /**
     * dummy data
     */
    public Long createDummy(Heritage heritage) {
        heritageRepository.save(heritage);
        return heritage.getId();
    }

    /**
     * heritage 조회
     */
    public Optional<Heritage> findOne(long id) {
        return heritageRepository.findById(id);
    }
}
