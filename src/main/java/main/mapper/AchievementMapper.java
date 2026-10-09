package main.mapper;

import main.dto.AchievementDto;
import main.model.Achievement;

public class AchievementMapper {
    public static AchievementDto toDto(Achievement achievement) {
        return new AchievementDto(
                achievement.getName(),
                achievement.getDescription()
        );
    }
}
