package main.observer;

import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;

public class GameEventManager {

    private final CopyOnWriteArrayList<GameObserver> observers =
            new CopyOnWriteArrayList<>();

    public void addObserver(GameObserver observer) {
        observers.addIfAbsent(
                Objects.requireNonNull(observer)
        );
    }

    public void removeObserver(GameObserver observer) {
        observers.remove(observer);
    }

    public void notifyObservers(GameEvent event) {

        for (GameObserver observer : observers) {

            observer.onGameEvent(event);
        }
    }
}
