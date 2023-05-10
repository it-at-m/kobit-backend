package de.muenchen.kobit.backend.validation.exception.contactpoint;

import de.muenchen.kobit.backend.validation.exception.ContactPointValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "invalid competence")
public class InvalidCompetenceException extends ContactPointValidationException {
    public InvalidCompetenceException(String message) {
        super(message);
    }
}
