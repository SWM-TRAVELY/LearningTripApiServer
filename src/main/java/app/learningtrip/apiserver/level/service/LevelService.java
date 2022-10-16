package app.learningtrip.apiserver.level.service;

import app.learningtrip.apiserver.level.domain.Level;
import app.learningtrip.apiserver.level.repository.LevelRepository;
import app.learningtrip.apiserver.user.domain.User;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class LevelService {

    private final LevelRepository levelRepository;

    /**
     * 레벨 조회
     */
    public String getLevel(User user) {
        List<Level> levelList = levelRepository.findAll();

        String levelName = "";
        for (Level level : levelList) {
            if (user.getExperiencePoint() >= level.getRequiredPoint()) {
                levelName = level.getName();
            } else {
                return levelName;
            }
        }
        return null;
    }

}
