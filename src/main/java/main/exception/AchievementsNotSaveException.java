package main.exception;

public class AchievementsNotSaveException extends RuntimeException {
    public AchievementsNotSaveException(String message) {
        super(message);
    }
}
