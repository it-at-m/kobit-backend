package de.muenchen.kobit.backend.validation.contactpoint;

import de.muenchen.kobit.backend.contactpoint.view.ContactPointView;
import de.muenchen.kobit.backend.validation.ContactPointValidator;
import de.muenchen.kobit.backend.validation.exception.contactpoint.InvalidCompetenceException;
import org.springframework.stereotype.Component;

@Component
public class ValidateCompetence implements ContactPointValidator<ContactPointView> {
    @Override
    public void validate(ContactPointView contactPointView) throws InvalidCompetenceException {
        if (contactPointView.getId() == null) {
            return;
        }
        if (contactPointView.getCompetences() == null) {
            throw new InvalidCompetenceException("Competences can not be null!");
        }
    }
}
