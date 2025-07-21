package com.moviebookingsystem.app.exceptions;

public class UsernameAndPasswordIncorrectException extends RuntimeException{
    public UsernameAndPasswordIncorrectException(String message) {
        super(message);
    }
}
