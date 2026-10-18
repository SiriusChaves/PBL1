package main.persistence.dao;

import main.dto.AchievementDto;
import main.exception.AchievementsNotLoadException;
import main.exception.AchievementsNotSaveException;

public interface AchievementDao {

    AchievementDto[] loadAchievements() throws AchievementsNotLoadException;

    void save(AchievementDto[] achievements) throws AchievementsNotSaveException;
}
