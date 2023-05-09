package de.muenchen.kobit.backend.validation.contactpoint;

import de.muenchen.kobit.backend.contact.view.ContactView;
import de.muenchen.kobit.backend.contactpoint.view.ContactPointView;
import de.muenchen.kobit.backend.validation.ContactPointValidator;
import de.muenchen.kobit.backend.validation.exception.ContactPointValidationException;
import de.muenchen.kobit.backend.validation.exception.contactpoint.InvalidContactException;
import de.muenchen.kobit.backend.validation.exception.contactpoint.InvalidContactPointException;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.stereotype.Component;

@Component
public class ValidateContact implements ContactPointValidator<ContactPointView> {

    private static final int EMAIL_MAX_SIZE = 500;

    @Override
    public void validate(ContactPointView contactPointView) throws ContactPointValidationException {
        if (contactPointView.getContact() == null) {
            throw new InvalidContactPointException("Contact can not be null.");
        } else if (contactPointView.getContact().size() < 1) {
            throw new InvalidContactPointException("At least one contact is needed.");
        } else {
            for (ContactView contact : contactPointView.getContact()) {
                if (!isMailValid(contact.getEmail())) {
                    throw new InvalidContactException("Contact need mail address is invalid.");
                }
                if (isEmailTooLarge(contact.getEmail())) {
                    throw new InvalidContactException(
                            "Contact must be not more than 500 characters.");
                }
            }
        }
    }

    private boolean isMailValid(String email) {
        return EmailValidator.getInstance().isValid(email);
    }

    private boolean isEmailTooLarge(String email) {
        return email.length() > EMAIL_MAX_SIZE;
    }
}
