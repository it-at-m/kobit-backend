package de.muenchen.kobit.backend.validation;

import de.muenchen.kobit.backend.contact.view.ContactView;
import de.muenchen.kobit.backend.contactpoint.view.ContactPointView;
import de.muenchen.kobit.backend.validation.exception.ContactPointValidationException;
import de.muenchen.kobit.backend.validation.exception.InvalidContactException;
import de.muenchen.kobit.backend.validation.exception.InvalidContactPointException;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.stereotype.Component;
@Component
public class ValidateContact implements Validator {
    @Override
    public void validate(ContactPointView contactPointView) throws ContactPointValidationException {
        if (contactPointView.getContact() == null) {
            throw new InvalidContactPointException("Contact can not be null!");
        } else if (contactPointView.getContact().size() < 1) {
            throw new InvalidContactPointException("At least one contact is needed!");
        } else {
            for (ContactView contact : contactPointView.getContact()) {
                if (!isMailValid(contact.getEmail())) {
                    throw new InvalidContactException("Contact need mail address is invalid!");
                }
            }
        }
    }

    private boolean isMailValid(String email) {
        return EmailValidator.getInstance().isValid(email);
    }
}
