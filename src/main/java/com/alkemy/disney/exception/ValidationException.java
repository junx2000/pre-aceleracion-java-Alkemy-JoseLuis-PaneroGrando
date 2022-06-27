package com.alkemy.disney.exception;

public class ValidationException extends RuntimeException{
    public ValidationException(String error) {
        super(error);
    }
}
