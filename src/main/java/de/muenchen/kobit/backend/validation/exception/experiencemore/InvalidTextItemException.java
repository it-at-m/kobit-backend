package de.muenchen.kobit.backend.validation.exception.experiencemore;

import de.muenchen.kobit.backend.validation.exception.TextItemValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Invalid text item.")
public class InvalidTextItemException extends TextItemValidationException {
    public InvalidTextItemException(String message) {
        super(message);
    }
}
