package de.muenchen.kobit.backend.validation.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "invalid link")
public class InvalidLinkException extends ContactPointValidationException {
    public InvalidLinkException(String message) {
        super(message);
    }
}
