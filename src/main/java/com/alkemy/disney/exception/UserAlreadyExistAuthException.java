package com.alkemy.disney.exception;

import org.springframework.security.core.AuthenticationException;

public class UserAlreadyExistAuthException extends AuthenticationException {
    public UserAlreadyExistAuthException(String error) {
        super(error);
    }
}
