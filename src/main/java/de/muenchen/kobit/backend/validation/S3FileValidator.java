package de.muenchen.kobit.backend.validation;

import de.muenchen.kobit.backend.validation.exception.S3FileValidationException;

public interface S3FileValidator<T> {
    void validate(T t) throws S3FileValidationException;
}
