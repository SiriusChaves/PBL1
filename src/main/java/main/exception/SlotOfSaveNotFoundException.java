package main.exception;

public class SlotOfSaveNotFoundException extends RuntimeException {
    public SlotOfSaveNotFoundException(String message) {
        super(message);
    }
}
