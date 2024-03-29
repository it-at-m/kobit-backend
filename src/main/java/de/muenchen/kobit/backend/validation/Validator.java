package de.muenchen.kobit.backend.validation;

import de.muenchen.kobit.backend.contactpoint.view.ContactPointView;
import de.muenchen.kobit.backend.validation.exception.ContactPointValidationException;

public interface Validator {
    void validate(ContactPointView contactPointView) throws ContactPointValidationException;
}
