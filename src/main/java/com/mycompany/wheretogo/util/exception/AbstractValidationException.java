package com.mycompany.wheretogo.util.exception;

public abstract class AbstractValidationException extends RuntimeException {
    public AbstractValidationException(String message) {
        super(message);
    }
}
