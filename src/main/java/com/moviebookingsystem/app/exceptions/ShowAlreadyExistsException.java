package com.moviebookingsystem.app.exceptions;

public class ShowAlreadyExistsException extends RuntimeException {
    public ShowAlreadyExistsException(String message) {
        super(message);
    }
}
