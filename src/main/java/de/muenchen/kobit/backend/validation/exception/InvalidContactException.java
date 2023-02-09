package de.muenchen.kobit.backend.validation.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "invalid contact")
public class InvalidContactException extends ContactPointValidationException {
    public InvalidContactException(String message) {
        super(message);
    }
}
