package main.model.achievement;

import main.dto.GameStateDto;

@FunctionalInterface
public interface AchievementUnlockStrategy {
    boolean canUnlock(GameStateDto gameState);
}
