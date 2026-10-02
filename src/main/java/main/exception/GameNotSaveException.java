package main.exception;

import java.io.IOException;

public class GameNotSaveException extends RuntimeException {
    public GameNotSaveException(String message) {
        super(message);
    }
}
