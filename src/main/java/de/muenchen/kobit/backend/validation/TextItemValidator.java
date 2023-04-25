package de.muenchen.kobit.backend.validation;

import de.muenchen.kobit.backend.validation.exception.TextItemValidationException;

public interface TextItemValidator<T> {
    void validate(T t) throws TextItemValidationException;
}
