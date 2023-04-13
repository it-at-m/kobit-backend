package de.muenchen.kobit.backend.validation;

import de.muenchen.kobit.backend.contactpoint.view.ContactPointView;
import de.muenchen.kobit.backend.validation.exception.InvalidContactPointException;
import org.springframework.stereotype.Component;

@Component
public class ValidateContactPoint implements Validator {

    private static final int NAME_MIN_SIZE = 5;
    private static final int SHORT_CUT_MIN = 3;
    private static final int SHORT_CUT_MAX = 10;
    private static final String NAME_PATTERN = "^[a-zA-ZäöüÄÖÜß]*$";
    private static final String SHORT_CUT_PATTERN = "^[a-zA-ZäöüÄÖÜß]*$";

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
        if (isNameToSmall(contactPointView.getName())) {
            throw new InvalidContactPointException("Name can not be less than 5 letters!");
        }
        if (!isNameValid(contactPointView.getName())) {
            throw new InvalidContactPointException("Name can only be letters!");
        }
        if (isShortCutOutOfRange(contactPointView.getShortCut())) {
            throw new InvalidContactPointException(
                    "ShortCut must be at least 3 letters and not more than 10!");
        }
        if (!isShortCutValid(contactPointView.getShortCut())) {
            throw new InvalidContactPointException("Shortcut can only be letters!");
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

    private boolean isShortCutOutOfRange(String shortcut) {
        return shortcut.length() < SHORT_CUT_MIN || shortcut.length() > SHORT_CUT_MAX;
    }

    private boolean isShortCutValid(String shortcut) {
        return shortcut.matches(SHORT_CUT_PATTERN);
    }

    private boolean isNameToSmall(String name) {
        return name.length() < NAME_MIN_SIZE;
    }

    private boolean isNameValid(String name) {
        return name.matches(NAME_PATTERN);
    }
}
