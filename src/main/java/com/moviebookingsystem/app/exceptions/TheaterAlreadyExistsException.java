package com.moviebookingsystem.app.exceptions;

public class TheaterAlreadyExistsException extends RuntimeException {
    public TheaterAlreadyExistsException(String message) {
        super(message);
    }
}
