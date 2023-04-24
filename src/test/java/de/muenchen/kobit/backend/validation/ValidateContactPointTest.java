package de.muenchen.kobit.backend.validation;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import de.muenchen.kobit.backend.contactpoint.view.ContactPointView;
import de.muenchen.kobit.backend.validation.contactpoint.ValidateContactPoint;
import de.muenchen.kobit.backend.validation.exception.contactpoint.InvalidContactPointException;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.Test;

class ValidateContactPointTest {

    private final ValidateContactPoint validateContactPoint = new ValidateContactPoint();

    @Test
    void validateTest_isValid() throws InvalidContactPointException {
        ContactPointView contactPointView =
                new ContactPointView(
                        UUID.randomUUID(),
                        "testName",
                        "test",
                        "test",
                        "test",
                        List.of(),
                        List.of(),
                        List.of());
        validateContactPoint.validate(contactPointView);
    }

    @Test
    void validateTest_InvalidNameNull() {
        ContactPointView contactPointView =
                new ContactPointView(
                        UUID.randomUUID(),
                        null,
                        "test",
                        "test",
                        "test",
                        List.of(),
                        List.of(),
                        List.of());
        InvalidContactPointException exception =
                assertThrows(
                        InvalidContactPointException.class,
                        () -> validateContactPoint.validate(contactPointView));
        assertThat(exception.getMessage())
                .isEqualTo("ContactPoint Fields name, shortCut and description can not be null!");
    }

    @Test
    void validateTest_InvalidNameShortCutNull() {
        ContactPointView contactPointView =
                new ContactPointView(
                        UUID.randomUUID(),
                        "testName",
                        null,
                        "test",
                        "test",
                        List.of(),
                        List.of(),
                        List.of());
        InvalidContactPointException exception =
                assertThrows(
                        InvalidContactPointException.class,
                        () -> validateContactPoint.validate(contactPointView));
        assertThat(exception.getMessage())
                .isEqualTo("ContactPoint Fields name, shortCut and description can not be null!");
    }

    @Test
    void validateTest_InvalidDescriptionNull() {
        ContactPointView contactPointView =
                new ContactPointView(
                        UUID.randomUUID(),
                        "testName",
                        "test",
                        null,
                        "test",
                        List.of(),
                        List.of(),
                        List.of());
        InvalidContactPointException exception =
                assertThrows(
                        InvalidContactPointException.class,
                        () -> validateContactPoint.validate(contactPointView));
        assertThat(exception.getMessage())
                .isEqualTo("ContactPoint Fields name, shortCut and description can not be null!");
    }

    @Test
    void validateTest_InvalidNameBlank() {
        ContactPointView contactPointView =
                new ContactPointView(
                        UUID.randomUUID(),
                        "  ",
                        "test",
                        "test",
                        "test",
                        List.of(),
                        List.of(),
                        List.of());
        InvalidContactPointException exception =
                assertThrows(
                        InvalidContactPointException.class,
                        () -> validateContactPoint.validate(contactPointView));
        assertThat(exception.getMessage())
                .isEqualTo("ContactPoint Fields name, shortCut and description can not be blank!");
    }

    @Test
    void validateTest_InvalidNameShortCutBlank() {
        ContactPointView contactPointView =
                new ContactPointView(
                        UUID.randomUUID(),
                        "testName",
                        "",
                        "test",
                        "test",
                        List.of(),
                        List.of(),
                        List.of());
        InvalidContactPointException exception =
                assertThrows(
                        InvalidContactPointException.class,
                        () -> validateContactPoint.validate(contactPointView));
        assertThat(exception.getMessage())
                .isEqualTo("ContactPoint Fields name, shortCut and description can not be blank!");
    }

    @Test
    void validateTest_InvalidDescriptionBlank() {
        ContactPointView contactPointView =
                new ContactPointView(
                        UUID.randomUUID(),
                        "testName",
                        "test",
                        "   ",
                        "test",
                        List.of(),
                        List.of(),
                        List.of());
        InvalidContactPointException exception =
                assertThrows(
                        InvalidContactPointException.class,
                        () -> validateContactPoint.validate(contactPointView));
        assertThat(exception.getMessage())
                .isEqualTo("ContactPoint Fields name, shortCut and description can not be blank!");
    }

    @Test
    void validateTest_InvalidShortCutShort() {
        ContactPointView contactPointView =
                new ContactPointView(
                        UUID.randomUUID(),
                        "testName",
                        "tt",
                        "test",
                        "test",
                        List.of(),
                        List.of(),
                        List.of());
        InvalidContactPointException exception =
                assertThrows(
                        InvalidContactPointException.class,
                        () -> validateContactPoint.validate(contactPointView));
        assertThat(exception.getMessage())
                .isEqualTo("ShortCut must be at least 3 letters and not more than 10!");
    }

    @Test
    void validateTest_InvalidShortCutLong() {
        ContactPointView contactPointView =
                new ContactPointView(
                        UUID.randomUUID(),
                        "testName",
                        "testTooLong",
                        "test",
                        "test",
                        List.of(),
                        List.of(),
                        List.of());
        InvalidContactPointException exception =
                assertThrows(
                        InvalidContactPointException.class,
                        () -> validateContactPoint.validate(contactPointView));
        assertThat(exception.getMessage())
                .isEqualTo("ShortCut must be at least 3 letters and not more than 10!");
    }

    @Test
    void validateTest_InvalidNameShort() {
        ContactPointView contactPointView =
                new ContactPointView(
                        UUID.randomUUID(),
                        "test",
                        "test",
                        "test",
                        "test",
                        List.of(),
                        List.of(),
                        List.of());
        InvalidContactPointException exception =
                assertThrows(
                        InvalidContactPointException.class,
                        () -> validateContactPoint.validate(contactPointView));
        assertThat(exception.getMessage()).isEqualTo("Name can not be less than 5 letters!");
    }

    @Test
    void validateTest_InvalidContactPointNull() {
        InvalidContactPointException exception =
                assertThrows(
                        InvalidContactPointException.class,
                        () -> validateContactPoint.validate(null));
        assertThat(exception.getMessage()).isEqualTo("ContactPointView can not be null!");
    }
}
