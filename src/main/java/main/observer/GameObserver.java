package main.observer;

@FunctionalInterface
public interface GameObserver {
    void onGameEvent(GameEvent event);
}
