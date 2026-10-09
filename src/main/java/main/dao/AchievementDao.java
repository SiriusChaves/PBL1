package main.dao;

import main.dto.AchievementDto;
import main.exception.AchievementsNotSaveException;
import main.model.Achievement;

public interface AchievementDao {

    AchievementDto[] loadAchievements() throws AchievementsNotSaveException;
    void save(AchievementDto[] achievements) throws AchievementsNotSaveException;
}
