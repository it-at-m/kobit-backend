package de.muenchen.kobit.backend.validation;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import de.muenchen.kobit.backend.contactpoint.view.ContactPointView;
import de.muenchen.kobit.backend.validation.contactpoint.ValidateContactPoint;
import de.muenchen.kobit.backend.validation.exception.contactpoint.InvalidContactPointException;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.Test;

class ValidateContactPointTest {

    private final ValidateContactPoint validateContactPoint = new ValidateContactPoint();

    @Test
    void validateTest_isValid() throws InvalidContactPointException, MalformedURLException {
        URL imageUrl = new URL("https://example.com/image.jpg");
        ContactPointView contactPointView =
                new ContactPointView(
                        UUID.randomUUID(),
                        "testName",
                        "test",
                        "test",
                        List.of("test"),
                        List.of(),
                        List.of(),
                        List.of(),
                        imageUrl);
        validateContactPoint.validate(contactPointView);
    }

    @Test
    void validateTest_InvalidNameNull() throws MalformedURLException {
        URL imageUrl = new URL("https://example.com/image.jpg");
        ContactPointView contactPointView =
                new ContactPointView(
                        UUID.randomUUID(),
                        null,
                        "test",
                        "test",
                        List.of("test"),
                        List.of(),
                        List.of(),
                        List.of(),
                        imageUrl);
        InvalidContactPointException exception =
                assertThrows(
                        InvalidContactPointException.class,
                        () -> validateContactPoint.validate(contactPointView));
        assertThat(exception.getMessage())
                .isEqualTo("ContactPoint Fields name, shortCut and description can not be null!");
    }

    @Test
    void validateTest_InvalidNameShortCutNull() throws MalformedURLException {
        URL imageUrl = new URL("https://example.com/image.jpg");
        ContactPointView contactPointView =
                new ContactPointView(
                        UUID.randomUUID(),
                        "testName",
                        null,
                        "test",
                        List.of("test"),
                        List.of(),
                        List.of(),
                        List.of(),
                        imageUrl);
        InvalidContactPointException exception =
                assertThrows(
                        InvalidContactPointException.class,
                        () -> validateContactPoint.validate(contactPointView));
        assertThat(exception.getMessage())
                .isEqualTo("ContactPoint Fields name, shortCut and description can not be null!");
    }

    @Test
    void validateTest_InvalidDescriptionNull() throws MalformedURLException {
        URL imageUrl = new URL("https://example.com/image.jpg");
        ContactPointView contactPointView =
                new ContactPointView(
                        UUID.randomUUID(),
                        "testName",
                        "test",
                        null,
                        List.of("test"),
                        List.of(),
                        List.of(),
                        List.of(),
                        imageUrl);
        InvalidContactPointException exception =
                assertThrows(
                        InvalidContactPointException.class,
                        () -> validateContactPoint.validate(contactPointView));
        assertThat(exception.getMessage())
                .isEqualTo("ContactPoint Fields name, shortCut and description can not be null!");
    }

    @Test
    void validateTest_InvalidNameBlank() throws MalformedURLException {
        URL imageUrl = new URL("https://example.com/image.jpg");
        ContactPointView contactPointView =
                new ContactPointView(
                        UUID.randomUUID(),
                        "  ",
                        "test",
                        "test",
                        List.of("test"),
                        List.of(),
                        List.of(),
                        List.of(),
                        imageUrl);
        InvalidContactPointException exception =
                assertThrows(
                        InvalidContactPointException.class,
                        () -> validateContactPoint.validate(contactPointView));
        assertThat(exception.getMessage())
                .isEqualTo("ContactPoint Fields name, shortCut and description can not be blank!");
    }

    @Test
    void validateTest_InvalidNameShortCutBlank() throws MalformedURLException {
        URL imageUrl = new URL("https://example.com/image.jpg");
        ContactPointView contactPointView =
                new ContactPointView(
                        UUID.randomUUID(),
                        "testName",
                        "",
                        "test",
                        List.of("test"),
                        List.of(),
                        List.of(),
                        List.of(),
                        imageUrl);
        InvalidContactPointException exception =
                assertThrows(
                        InvalidContactPointException.class,
                        () -> validateContactPoint.validate(contactPointView));
        assertThat(exception.getMessage())
                .isEqualTo("ContactPoint Fields name, shortCut and description can not be blank!");
    }

    @Test
    void validateTest_InvalidDescriptionBlank() throws MalformedURLException {
        URL imageUrl = new URL("https://example.com/image.jpg");
        ContactPointView contactPointView =
                new ContactPointView(
                        UUID.randomUUID(),
                        "testName",
                        "test",
                        "   ",
                        List.of("test"),
                        List.of(),
                        List.of(),
                        List.of(),
                        imageUrl);
        InvalidContactPointException exception =
                assertThrows(
                        InvalidContactPointException.class,
                        () -> validateContactPoint.validate(contactPointView));
        assertThat(exception.getMessage())
                .isEqualTo("ContactPoint Fields name, shortCut and description can not be blank!");
    }

    @Test
    void validateTest_InvalidShortCutShort() throws MalformedURLException {
        URL imageUrl = new URL("https://example.com/image.jpg");
        ContactPointView contactPointView =
                new ContactPointView(
                        UUID.randomUUID(),
                        "testName",
                        "tt",
                        "test",
                        List.of("test"),
                        List.of(),
                        List.of(),
                        List.of(),
                        imageUrl);
        InvalidContactPointException exception =
                assertThrows(
                        InvalidContactPointException.class,
                        () -> validateContactPoint.validate(contactPointView));
        assertThat(exception.getMessage())
                .isEqualTo("ShortCut must be at least 3 letters and not more than 10!");
    }

    @Test
    void validateTest_InvalidShortCutLong() throws MalformedURLException {
        URL imageUrl = new URL("https://example.com/image.jpg");
        ContactPointView contactPointView =
                new ContactPointView(
                        UUID.randomUUID(),
                        "testName",
                        "testTooLong",
                        "test",
                        List.of("test"),
                        List.of(),
                        List.of(),
                        List.of(),
                        imageUrl);
        InvalidContactPointException exception =
                assertThrows(
                        InvalidContactPointException.class,
                        () -> validateContactPoint.validate(contactPointView));
        assertThat(exception.getMessage())
                .isEqualTo("ShortCut must be at least 3 letters and not more than 10!");
    }

    @Test
    void validateTest_InvalidNameShort() throws MalformedURLException {
        URL imageUrl = new URL("https://example.com/image.jpg");
        ContactPointView contactPointView =
                new ContactPointView(
                        UUID.randomUUID(),
                        "test",
                        "test",
                        "test",
                        List.of("test"),
                        List.of(),
                        List.of(),
                        List.of(),
                        imageUrl);
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
