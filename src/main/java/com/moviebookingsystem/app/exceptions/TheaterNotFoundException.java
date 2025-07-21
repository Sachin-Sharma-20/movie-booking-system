package com.moviebookingsystem.app.exceptions;

public class TheaterNotFoundException extends RuntimeException {
    public TheaterNotFoundException(String message) {
        super(message);
    }
}
