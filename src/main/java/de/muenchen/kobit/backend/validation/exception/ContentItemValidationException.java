package de.muenchen.kobit.backend.validation.exception;

public abstract class ContentItemValidationException extends Exception {
    protected ContentItemValidationException(String message) {
        super(message);
    }
}
