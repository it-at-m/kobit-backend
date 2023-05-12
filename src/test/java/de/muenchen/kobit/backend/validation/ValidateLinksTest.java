package de.muenchen.kobit.backend.validation;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import de.muenchen.kobit.backend.contactpoint.model.ContactPoint;
import de.muenchen.kobit.backend.contactpoint.view.ContactPointView;
import de.muenchen.kobit.backend.links.view.LinkView;
import de.muenchen.kobit.backend.validation.contactpoint.ValidateLinks;
import de.muenchen.kobit.backend.validation.exception.contactpoint.InvalidLinkException;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.Test;

class ValidateLinksTest {

    private final ValidateLinks validateLinks = new ValidateLinks();

    @Test
    void validateTest_isValid() throws InvalidLinkException, MalformedURLException {
        UUID id = UUID.randomUUID();
        URL imageUrl = new URL("https://example.com/image.jpg");
        ContactPointView contactPointView =
                new ContactPointView(
                        id,
                        "test",
                        "test",
                        "test",
                        List.of("test"),
                        List.of(),
                        List.of(),
                        List.of(new LinkView(id, "link", "https://google.com", false)),
                        imageUrl);
        validateLinks.validate(contactPointView);
    }

    @Test
    void validateTest_isValidContactPointIdNull() throws InvalidLinkException, MalformedURLException {
        UUID id = UUID.randomUUID();
        URL imageUrl = new URL("https://example.com/image.jpg");
        ContactPointView contactPointView =
                new ContactPointView(
                        id,
                        "test",
                        "test",
                        "test",
                        List.of("test"),
                        List.of(),
                        List.of(),
                        List.of(new LinkView(null, "link", "https://google.com", false)),
                        imageUrl);
        validateLinks.validate(contactPointView);
    }

    @Test
    void validateTest_InvalidURL() throws MalformedURLException {
        UUID id = UUID.randomUUID();
        URL imageUrl = new URL("https://example.com/image.jpg");
        ContactPointView contactPointView =
                new ContactPointView(
                        id,
                        "test",
                        "test",
                        "test",
                        List.of("test"),
                        List.of(),
                        List.of(),
                        List.of(new LinkView(id, "link", "https://google..com", false)),
                        imageUrl);
        InvalidLinkException exception =
                assertThrows(
                        InvalidLinkException.class, () -> validateLinks.validate(contactPointView));
        assertThat(exception.getMessage()).isEqualTo("Link url is not valid!");
    }

    @Test
    void validateTest_InvalidURLNull() throws MalformedURLException {
        URL imageUrl = new URL("https://example.com/image.jpg");
        UUID id = UUID.randomUUID();
        ContactPointView contactPointView =
                new ContactPointView(
                        id,
                        "test",
                        "test",
                        "test",
                        List.of("test"),
                        List.of(),
                        List.of(),
                        List.of(new LinkView(id, "link", null, false)),
                        imageUrl);
        InvalidLinkException exception =
                assertThrows(
                        InvalidLinkException.class, () -> validateLinks.validate(contactPointView));
        assertThat(exception.getMessage()).isEqualTo("Link fields name and url can not be null!");
    }

    @Test
    void validateTest_InvalidNameNull() throws MalformedURLException {
        UUID id = UUID.randomUUID();
        URL imageUrl = new URL("https://example.com/image.jpg");
        ContactPointView contactPointView =
                new ContactPointView(
                        id,
                        "test",
                        "test",
                        "test",
                        List.of("test"),
                        List.of(),
                        List.of(),
                        List.of(new LinkView(id, null, "https://google.com", false)),
                        imageUrl);
        InvalidLinkException exception =
                assertThrows(
                        InvalidLinkException.class, () -> validateLinks.validate(contactPointView));
        assertThat(exception.getMessage()).isEqualTo("Link fields name and url can not be null!");
    }
}
