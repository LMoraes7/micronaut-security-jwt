package com.example.domain.exception;

public class InvalidCredentials extends RuntimeException {

    public InvalidCredentials() {
        super();
    }

    public InvalidCredentials(String message) {
        super(message);
    }
}
