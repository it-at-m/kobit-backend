package de.muenchen.kobit.backend.additional.serviceagreement;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Invalid Step Count")
public class UnknownStepException extends Exception {
    public UnknownStepException(String message) {
        super(message);
    }
}
