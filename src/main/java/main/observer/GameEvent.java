package main.observer;

public record GameEvent(
        GameEventType type,
        Object payload
) {}
