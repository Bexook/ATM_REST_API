package com.example.atm.exceptions;

public class DataNotFoundException extends Exception {

    private String message;

    public DataNotFoundException(String message) {
        this.message = message;
    }
}
