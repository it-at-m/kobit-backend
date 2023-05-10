package de.muenchen.kobit.backend.validation;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import de.muenchen.kobit.backend.competence.Competence;
import de.muenchen.kobit.backend.contactpoint.view.ContactPointView;
import de.muenchen.kobit.backend.validation.contactpoint.ValidateCompetence;
import de.muenchen.kobit.backend.validation.exception.contactpoint.InvalidCompetenceException;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.Test;

class ValidateCompetenceTest {

    private final ValidateCompetence validateCompetence = new ValidateCompetence();

    @Test
    void validateTest_isValid() throws InvalidCompetenceException {
        ContactPointView contactPointView =
                new ContactPointView(
                        UUID.randomUUID(),
                        "test",
                        "test",
                        "test",
                        List.of("test"),
                        List.of(),
                        List.of(Competence.DISCRIMINATION),
                        List.of());
        validateCompetence.validate(contactPointView);
    }

    @Test
    void validateTest_isInvalidEmpty() {
        ContactPointView contactPointView =
                new ContactPointView(
                        UUID.randomUUID(),
                        "test",
                        "test",
                        "test",
                        List.of("test"),
                        List.of(),
                        List.of(),
                        List.of());
        InvalidCompetenceException exception =
                assertThrows(
                        InvalidCompetenceException.class,
                        () -> validateCompetence.validate(contactPointView));
        assertThat(exception.getMessage()).isEqualTo("At least one Competence is needed!");
    }

    @Test
    void validateTest_isInvalidNull() {
        ContactPointView contactPointView =
                new ContactPointView(
                        UUID.randomUUID(),
                        "test",
                        "test",
                        "test",
                        List.of("test"),
                        List.of(),
                        null,
                        List.of());
        InvalidCompetenceException exception =
                assertThrows(
                        InvalidCompetenceException.class,
                        () -> validateCompetence.validate(contactPointView));
        assertThat(exception.getMessage()).isEqualTo("Competences can not be null!");
    }
}
