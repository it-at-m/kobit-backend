package de.muenchen.kobit.backend.validation.exception.offer;

import de.muenchen.kobit.backend.validation.exception.OfferValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Invalid offer")
public class InvalidOfferException extends OfferValidationException {
    public InvalidOfferException(String message) {
        super(message);
    }
}
