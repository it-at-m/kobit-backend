package de.muenchen.kobit.backend.validation;

import de.muenchen.kobit.backend.validation.exception.ValidationException;

public interface Validator<T> {
    void validate(T view) throws ValidationException;
}
