package main.exception;

public class AchievementsNotLoadException extends RuntimeException {
    public AchievementsNotLoadException(String message) {
        super(message);
    }
}
