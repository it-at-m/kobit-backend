package de.muenchen.kobit.backend.validation;

import de.muenchen.kobit.backend.contactpoint.view.ContactPointView;
import de.muenchen.kobit.backend.validation.exception.InvalidCompetenceException;
import org.springframework.stereotype.Component;

@Component
public class ValidateCompetence implements Validator {
    @Override
    public void validate(ContactPointView contactPointView) throws InvalidCompetenceException {

        if (contactPointView.getCompetences() == null) {
            throw new InvalidCompetenceException("Competences can not be null!");
        }
        if (contactPointView.getCompetences().size() < 1) {
            throw new InvalidCompetenceException("At least one Competence is needed!");
        }
    }
}
