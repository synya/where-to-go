package com.mycompany.wheretogo.util.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.UNPROCESSABLE_ENTITY, reason = "No data found")
public class NotFoundException extends AbstractValidationException {
    public NotFoundException(String message) {
        super(message);
    }
}