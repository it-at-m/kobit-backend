package de.muenchen.kobit.backend.validation.exception;

public abstract class S3FileValidationException extends Exception {
    protected S3FileValidationException(String message) {
        super(message);
    }
}
