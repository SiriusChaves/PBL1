package main.persistence.dao;

import main.dto.AchievementDto;
import main.exception.AchievementsNotSaveException;

public interface AchievementDao {

    AchievementDto[] loadAchievements() throws AchievementsNotSaveException;

    void save(AchievementDto[] achievements) throws AchievementsNotSaveException;
}
