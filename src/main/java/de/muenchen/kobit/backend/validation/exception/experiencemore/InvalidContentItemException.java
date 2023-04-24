package de.muenchen.kobit.backend.validation.exception.experiencemore;

import de.muenchen.kobit.backend.validation.exception.ContentItemValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "invalid content item")
public class InvalidContentItemException extends ContentItemValidationException {
    public InvalidContentItemException(String message) {
        super(message);
    }
}
