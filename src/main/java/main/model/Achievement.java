package main.model;

import main.dto.GameStateDto;

import java.util.function.Predicate;

public class Achievement {
    private final String id;
    private final String name;
    private final String description;
    private final Predicate<GameStateDto> requiresToUnlock;

    public Achievement(String id, String name, String description, Predicate<GameStateDto> predicate) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.requiresToUnlock = predicate;
    }

    public boolean isUnlocked(GameStateDto gameState) {
        return requiresToUnlock.test(gameState);
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
