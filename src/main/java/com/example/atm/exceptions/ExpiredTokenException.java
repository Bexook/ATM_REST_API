package com.example.atm.exceptions;

public class ExpiredTokenException extends Exception{
    private String message;

    public ExpiredTokenException(String message) {
        super(message);
    }
}
