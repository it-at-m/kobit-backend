package de.muenchen.kobit.backend.validation.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "invalid contact point")
public class InvalidContactPointException extends ContactPointValidationException {
    public InvalidContactPointException(String message) {
        super(message);
    }
}
