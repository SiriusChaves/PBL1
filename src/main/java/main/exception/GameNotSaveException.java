package main.exception;

import java.io.IOException;

public class GameNotSaveException extends Exception {
    public GameNotSaveException(String message) {
        super(message);
    }
}
