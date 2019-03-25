package com.mycompany.wheretogo.util.exception;

public class NotFoundException extends AbstractValidationException {
    public NotFoundException(String message) {
        super(message);
    }
}