package de.muenchen.kobit.backend.validation.exception;

public abstract class OfferValidationException extends Exception {
    protected OfferValidationException(String message) {
        super(message);
    }
}
