package main.model.achievement;

import main.dto.GameStateDto;

import java.util.function.Predicate;

public class Achievement {
    private final String id;
    private final String name;
    private final String description;
    private final AchievementUnlockStrategy unlockStrategy;

    public Achievement(
            String id,
            String name,
            String description,
            AchievementUnlockStrategy unlockStrategy) {

        this.id = id;
        this.name = name;
        this.description = description;
        this.unlockStrategy = unlockStrategy;
    }

    public boolean isUnlocked(GameStateDto gameState) {
        return unlockStrategy.canUnlock(gameState);
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}
