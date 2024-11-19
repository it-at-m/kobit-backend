package de.muenchen.kobit.backend.validation;

import de.muenchen.kobit.backend.validation.exception.OfferValidationException;

public interface OfferValidator<T> {
    void validate(T t) throws OfferValidationException;
}
