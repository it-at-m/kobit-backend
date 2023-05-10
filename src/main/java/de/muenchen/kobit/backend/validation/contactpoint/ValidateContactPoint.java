package de.muenchen.kobit.backend.validation.contactpoint;

import de.muenchen.kobit.backend.contactpoint.view.ContactPointView;
import de.muenchen.kobit.backend.validation.ContactPointValidator;
import de.muenchen.kobit.backend.validation.exception.contactpoint.InvalidContactPointException;
import org.springframework.stereotype.Component;

@Component
public class ValidateContactPoint implements ContactPointValidator<ContactPointView> {

    private static final int NAME_MIN_SIZE = 5;
    private static final int NAME_MAX_SIZE = 100;
    private static final int SHORT_CUT_MIN = 3;
    private static final int SHORT_CUT_MAX = 10;
    private static final int DESCRIPTION_MAX_SIZE = 2000;

    @Override
    public void validate(ContactPointView contactPointView) throws InvalidContactPointException {
        if (contactPointView == null) {
            throw new InvalidContactPointException("ContactPointView can not be null!");
        }
        if (anyFieldNull(contactPointView)) {
            throw new InvalidContactPointException(
                    "ContactPoint Fields name, shortCut and description can not be null!");
        }
        if (anyFieldBlank(contactPointView)) {
            throw new InvalidContactPointException(
                    "ContactPoint Fields name, shortCut and description can not be blank!");
        }
        if (isNameOutOfRange(contactPointView.getName())) {
            throw new InvalidContactPointException(
                    "Name must be at least 4 characters and not more than 100!");
        }
        if (isShortCutOutOfRange(contactPointView.getShortCut())) {
            throw new InvalidContactPointException(
                    "ShortCut must be at least 3 letters and not more than 10!");
        }
        if (isDescriptionTooLarge(contactPointView.getDescription())) {
            throw new InvalidContactPointException(
                    "Description must be not more than 2000 characters!");
        }
    }

    private boolean anyFieldNull(ContactPointView contactPointView) {
        return (contactPointView.getName() == null
                || contactPointView.getShortCut() == null
                || contactPointView.getDescription() == null);
    }

    private boolean anyFieldBlank(ContactPointView contactPointView) {
        return (contactPointView.getName().isBlank()
                || contactPointView.getShortCut().isBlank()
                || contactPointView.getDescription().isBlank());
    }

    private boolean isNameOutOfRange(String name) {
        return name.length() < NAME_MIN_SIZE || name.length() > NAME_MAX_SIZE;
    }

    private boolean isShortCutOutOfRange(String shortcut) {
        return shortcut.length() < SHORT_CUT_MIN || shortcut.length() > SHORT_CUT_MAX;
    }

    private boolean isDescriptionTooLarge(String description) {
        return description.length() > DESCRIPTION_MAX_SIZE;
    }
}
