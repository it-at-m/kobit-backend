package de.muenchen.kobit.backend.validation;

import static org.junit.jupiter.api.Assertions.assertThrows;

import de.muenchen.kobit.backend.contact.view.ContactView;
import de.muenchen.kobit.backend.contactpoint.view.ContactPointView;
import de.muenchen.kobit.backend.validation.exception.ContactPointValidationException;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.Test;

class ValidateContactTest {

    private final ValidateContact validateContact = new ValidateContact();

    @Test
    void validateTest_isValid() throws ContactPointValidationException {
        ContactPointView contactPointView =
                new ContactPointView(
                        UUID.randomUUID(),
                        "test",
                        "test",
                        "test",
                        List.of("test"),
                        List.of(new ContactView("mail@mail.de")),
                        List.of(),
                        List.of());
        validateContact.validate(contactPointView);
    }

    @Test
    void validateTest_isInValid() {
        ContactPointView contactPointView =
                new ContactPointView(
                        UUID.randomUUID(),
                        "test",
                        "test",
                        "test",
                        List.of("test"),
                        List.of(new ContactView("mail@mail....de")),
                        List.of(),
                        List.of());
        assertThrows(
                ContactPointValidationException.class,
                () -> validateContact.validate(contactPointView));
    }
}
