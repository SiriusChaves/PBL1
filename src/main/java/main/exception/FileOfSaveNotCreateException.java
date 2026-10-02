package main.exception;

public class FileOfSaveNotCreateException extends RuntimeException {
    public FileOfSaveNotCreateException(String message) {
        super(message);
    }
}
