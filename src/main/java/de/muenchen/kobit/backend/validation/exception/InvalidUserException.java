package de.muenchen.kobit.backend.validation.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(
        code = HttpStatus.BAD_REQUEST,
        reason = "User has not the needed permission to performe this action!")
public class InvalidUserException extends ContactPointValidationException {
    public InvalidUserException(String message) {
        super(message);
    }
}
