package com.example.exception;

public class InvalidUsernameOrPasswordException extends RuntimeException {
    public InvalidUsernameOrPasswordException(String message) {
        super(message);
    }
}
