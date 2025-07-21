package com.moviebookingsystem.app.exceptions;

public class BookingAlreadyRegisteredException extends RuntimeException {
    public BookingAlreadyRegisteredException(String message) {
        super(message);
    }
}
