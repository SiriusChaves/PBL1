package main.exception;

public class UserProfileErrorException extends RuntimeException {
    public UserProfileErrorException(String message) {
        super(message);
    }
}
