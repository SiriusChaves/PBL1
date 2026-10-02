package main.exception;

public class SaveSlotsFullException extends RuntimeException {
    public SaveSlotsFullException(String message) {
        super(message);
    }
}
