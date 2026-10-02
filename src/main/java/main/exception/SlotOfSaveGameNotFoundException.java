package main.exception;

public class SlotOfSaveGameNotFoundException extends RuntimeException {
    public SlotOfSaveGameNotFoundException(String message) {
        super(message);
    }
}
