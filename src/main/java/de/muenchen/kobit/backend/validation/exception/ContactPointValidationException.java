package de.muenchen.kobit.backend.validation.exception;

public abstract class ContactPointValidationException extends Exception {
    protected ContactPointValidationException(String message) {
        super(message);
    }
}
