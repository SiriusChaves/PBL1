package main.exception;

import java.io.IOException;

public class GameNotSaveException extends IOException {
    public GameNotSaveException(String message) {
        super(message);
    }
}
