package de.muenchen.kobit.backend.contactpoint;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "contact point not found")
public class ContactPointNotFoundException extends Exception {
    public ContactPointNotFoundException(String message) {
        super(message);
    }
}
