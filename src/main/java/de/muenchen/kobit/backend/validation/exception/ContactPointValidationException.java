package de.muenchen.kobit.backend.validation.exception;

public abstract class ContactPointValidationException extends Exception {
    ContactPointValidationException(String message) {
        super(message);
    }
}
