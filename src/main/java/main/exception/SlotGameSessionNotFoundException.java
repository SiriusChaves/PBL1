package main.exception;

public class SlotGameSessionNotFoundException extends RuntimeException {
    public SlotGameSessionNotFoundException(String message) {
        super(message);
    }
}
