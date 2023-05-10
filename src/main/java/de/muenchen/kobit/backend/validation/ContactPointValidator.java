package de.muenchen.kobit.backend.validation;

import de.muenchen.kobit.backend.validation.exception.ContactPointValidationException;

public interface ContactPointValidator<T> {
    void validate(T t) throws ContactPointValidationException;
}
