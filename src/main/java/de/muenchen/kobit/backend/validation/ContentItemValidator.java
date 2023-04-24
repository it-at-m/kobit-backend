package de.muenchen.kobit.backend.validation;

import de.muenchen.kobit.backend.validation.exception.ContentItemValidationException;

public interface ContentItemValidator<T> {
    void validate(T t) throws ContentItemValidationException;
}
