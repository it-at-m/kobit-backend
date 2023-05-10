package de.muenchen.kobit.backend.validation.exception;

public abstract class TextItemValidationException extends Exception {
    protected TextItemValidationException(String message) {
        super(message);
    }
}
