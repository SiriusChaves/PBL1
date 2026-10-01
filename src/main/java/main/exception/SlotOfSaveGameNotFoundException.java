package main.exception;

import java.io.IOException;

public class SlotGameSessionNotFoundException extends Exception {
    public SlotGameSessionNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
