package de.muenchen.kobit.backend.validation;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import de.muenchen.kobit.backend.offer.view.OfferView;
import de.muenchen.kobit.backend.validation.exception.OfferValidationException;
import de.muenchen.kobit.backend.validation.exception.offer.InvalidOfferException;
import de.muenchen.kobit.backend.validation.offer.ValidateOffer;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDate;
import java.util.UUID;
import org.junit.jupiter.api.Test;

public class ValidateOfferTest {

    @Test
    void testValidOffer() throws MalformedURLException, OfferValidationException {
        var imageLink = new URL("https://example.com/image.jpg");
        var id = UUID.randomUUID();
        var view =
                new OfferView(
                        id,
                        "2023-01-01",
                        "2023-12-31",
                        "Test title",
                        "Test description",
                        imageLink);
        var validator = new ValidateOffer();

        // No exception should be thrown
        validator.validate(view);
    }

    @Test
    void testInvalidStartDate() throws MalformedURLException, OfferValidationException {
        var imageLink = new URL("https://example.com/image.jpg");
        var id = UUID.randomUUID();
        var view =
                new OfferView(
                        id,
                        "2023/01/01",
                        "2023-12-31",
                        "Test title",
                        "Test description",
                        imageLink);
        var validator = new ValidateOffer();

        // Expecting InvalidOfferException to be thrown
        assertThrows(InvalidOfferException.class, () -> validator.validate(view));
    }

    @Test
    void testInvalidEndDate() throws MalformedURLException, OfferValidationException {
        var imageLink = new URL("https://example.com/image.jpg");
        var id = UUID.randomUUID();
        var view =
                new OfferView(
                        id,
                        "2023-01-01",
                        "31/12/2023",
                        "Test title",
                        "Test description",
                        imageLink);
        var validator = new ValidateOffer();

        // Expecting InvalidOfferException to be thrown
        assertThrows(InvalidOfferException.class, () -> validator.validate(view));
    }

    @Test
    void testInvalidDateOrder() throws MalformedURLException, OfferValidationException {
        var imageLink = new URL("https://example.com/image.jpg");
        var id = UUID.randomUUID();
        var startDate = LocalDate.parse("2023-02-01");
        var endDate = LocalDate.parse("2023-01-01");
        var view =
                new OfferView(
                        id,
                        startDate.toString(),
                        endDate.toString(),
                        "Test title",
                        "Test description",
                        imageLink);
        var validator = new ValidateOffer();

        // Expecting InvalidOfferException to be thrown for both date order rules
        assertThrows(InvalidOfferException.class, () -> validator.validate(view));
    }

    @Test
    void testValidDateOrder() throws MalformedURLException, OfferValidationException {
        var imageLink = new URL("https://example.com/image.jpg");
        var id = UUID.randomUUID();
        var startDate = LocalDate.parse("2023-01-01");
        var endDate = LocalDate.parse("2023-02-01");
        var view =
                new OfferView(
                        id,
                        startDate.toString(),
                        endDate.toString(),
                        "Test title",
                        "Test description",
                        imageLink);
        var validator = new ValidateOffer();

        // No exception should be thrown
        validator.validate(view);
    }

    @Test
    void testTitleNull() throws MalformedURLException {
        var imageLink = new URL("https://example.com/image.jpg");
        var id = UUID.randomUUID();
        var startDate = LocalDate.parse("2023-01-01");
        var endDate = LocalDate.parse("2023-02-01");
        OfferView offerView =
                new OfferView(
                        id,
                        startDate.toString(),
                        endDate.toString(),
                        null,
                        "Test description",
                        imageLink);
        offerView.setTitle(null);
        var validator = new ValidateOffer();

        assertThrows(
                InvalidOfferException.class,
                () -> {
                    validator.validate(offerView);
                });
    }

    @Test
    void testTitleTooShort() throws MalformedURLException {
        var imageLink = new URL("https://example.com/image.jpg");
        var id = UUID.randomUUID();
        var startDate = LocalDate.parse("2023-01-01");
        var endDate = LocalDate.parse("2023-02-01");
        OfferView offerView =
                new OfferView(
                        id,
                        startDate.toString(),
                        endDate.toString(),
                        "abc",
                        "Test description",
                        imageLink);
        var validator = new ValidateOffer();

        assertThrows(
                InvalidOfferException.class,
                () -> {
                    validator.validate(offerView);
                });
    }

    @Test
    void testTitleMaxLengthExceeded() throws MalformedURLException {
        var imageLink = new URL("https://example.com/image.jpg");
        var id = UUID.randomUUID();
        var startDate = LocalDate.parse("2023-01-01");
        var endDate = LocalDate.parse("2023-02-01");
        OfferView offerView =
                new OfferView(
                        id,
                        startDate.toString(),
                        endDate.toString(),
                        "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vivamus dapibus,"
                            + " lorem et. Lorem ipsum dolor sit amet, consectetur adipiscing elit."
                            + " Vivamus dapibus, lorem et.",
                        "Test description",
                        imageLink);
        var validator = new ValidateOffer();

        assertThrows(
                InvalidOfferException.class,
                () -> {
                    validator.validate(offerView);
                });
    }

    @Test
    void testValidTitle() throws MalformedURLException {
        var imageLink = new URL("https://example.com/image.jpg");
        var id = UUID.randomUUID();
        var startDate = LocalDate.parse("2023-01-01");
        var endDate = LocalDate.parse("2023-02-01");
        OfferView offerView =
                new OfferView(
                        id,
                        startDate.toString(),
                        endDate.toString(),
                        "Valid Title",
                        "Test description",
                        imageLink);
        var validator = new ValidateOffer();

        assertDoesNotThrow(
                () -> {
                    validator.validate(offerView);
                });
    }

    @Test
    void testDescriptionNull() throws MalformedURLException {
        var imageLink = new URL("https://example.com/image.jpg");
        var id = UUID.randomUUID();
        var startDate = LocalDate.parse("2023-01-01");
        var endDate = LocalDate.parse("2023-02-01");
        OfferView offerView =
                new OfferView(
                        id,
                        startDate.toString(),
                        endDate.toString(),
                        "Valid Title",
                        null,
                        imageLink);
        var validator = new ValidateOffer();

        assertThrows(
                InvalidOfferException.class,
                () -> {
                    validator.validate(offerView);
                });
    }

    @Test
    void testDescriptionTooShort() throws MalformedURLException {
        var imageLink = new URL("https://example.com/image.jpg");
        var id = UUID.randomUUID();
        var startDate = LocalDate.parse("2023-01-01");
        var endDate = LocalDate.parse("2023-02-01");
        OfferView offerView =
                new OfferView(
                        id,
                        startDate.toString(),
                        endDate.toString(),
                        "Valid Title",
                        "Short",
                        imageLink);
        var validator = new ValidateOffer();

        assertThrows(
                InvalidOfferException.class,
                () -> {
                    validator.validate(offerView);
                });
    }

    @Test
    void testDescriptionMaxLengthExceeded() throws MalformedURLException {
        var imageLink = new URL("https://example.com/image.jpg");
        var id = UUID.randomUUID();
        var startDate = LocalDate.parse("2023-01-01");
        var endDate = LocalDate.parse("2023-02-01");
        OfferView offerView =
                new OfferView(
                        id,
                        startDate.toString(),
                        endDate.toString(),
                        "Valid Title",
                        "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vivamus dapibus,"
                            + " lorem et. Lorem ipsum dolor sit amet, consectetur adipiscing elit."
                            + " Vivamus dapibus, lorem et. Lorem ipsum dolor sit amet, consectetur"
                            + " adipiscing elit. Vivamus dapibus, lorem et. Lorem ipsum dolor sit"
                            + " amet, consectetur adipiscing elit. Vivamus dapibus, lorem et."
                            + " Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vivamus"
                            + " dapibus, lorem et. Lorem ipsum dolor sit amet, consectetur"
                            + " adipiscing elit. Vivamus dapibus, lorem et. Lorem ipsum dolor sit"
                            + " amet, consectetur adipiscing elit. Vivamus dapibus, lorem et."
                            + " Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vivamus"
                            + " dapibus, lorem et. Lorem ipsum dolor sit amet, consectetur"
                            + " adipiscing elit. Vivamus dapibus, lorem et. Lorem ipsum dolor sit"
                            + " amet, consectetur adipiscing elit. Vivamus dapibus, lorem et."
                            + " Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vivamus"
                            + " dapibus, lorem et. Lorem ipsum dolor sit amet, consectetur"
                            + " adipiscing elit. Vivamus dapibus, lorem et. Lorem ipsum dolor sit"
                            + " amet, consectetur adipiscing elit. Vivamus dapibus, lorem et."
                            + " Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vivamus"
                            + " dapibus, lorem et. Lorem ipsum dolor sit amet, consectetur"
                            + " adipiscing elit. Vivamus dapibus, lorem et. Lorem ipsum dolor sit"
                            + " amet, consectetur adipiscing elit. Vivamus dapibus, lorem et."
                            + " Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vivamus"
                            + " dapibus, lorem et. Lorem ipsum dolor sit amet, consectetur"
                            + " adipiscing elit. Vivamus dapibus, lorem et. Lorem ipsum dolor sit"
                            + " amet, consectetur adipiscing elit. Vivamus dapibus, lorem et."
                            + " Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vivamus"
                            + " dapibus, lorem et. Lorem ipsum dolor sit amet, consectetur"
                            + " adipiscing elit. Vivamus dapibus, lorem et. Lorem ipsum dolor sit"
                            + " amet, consectetur adipiscing elit. Vivamus dapibus, lorem et."
                            + " Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vivamus"
                            + " dapibus, lorem et. Lorem ipsum dolor sit amet, consectetur"
                            + " adipiscing elit. Vivamus dapibus, lorem et. Lorem ipsum dolor sit"
                            + " amet, consectetur adipiscing elit. Vivamus dapibus, lorem et."
                            + " Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vivamus"
                            + " dapibus, lorem et. Lorem ipsum dolor sit amet, consectetur"
                            + " adipiscing elit. Vivamus dapibus, lorem et. Lorem ipsum dolor sit"
                            + " amet, consectetur adipiscing elit. Vivamus dapibus, lorem et."
                            + " Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vivamus"
                            + " dapibus, lorem et. Lorem ipsum dolor sit amet, consectetur"
                            + " adipiscing elit. Vivamus dapibus, lorem et.",
                        imageLink);
        var validator = new ValidateOffer();

        assertThrows(
                InvalidOfferException.class,
                () -> {
                    validator.validate(offerView);
                });
    }

    @Test
    void testValidDescription() throws MalformedURLException {
        var imageLink = new URL("https://example.com/image.jpg");
        var id = UUID.randomUUID();
        var startDate = LocalDate.parse("2023-01-01");
        var endDate = LocalDate.parse("2023-02-01");
        OfferView offerView =
                new OfferView(
                        id,
                        startDate.toString(),
                        endDate.toString(),
                        "Valid Title",
                        "Valid description",
                        imageLink);
        var validator = new ValidateOffer();

        assertDoesNotThrow(
                () -> {
                    validator.validate(offerView);
                });
    }

    @Test
    void testImageNull() throws MalformedURLException {

        var id = UUID.randomUUID();
        var startDate = LocalDate.parse("2023-01-01");
        var endDate = LocalDate.parse("2023-02-01");
        OfferView offerView =
                new OfferView(
                        id,
                        startDate.toString(),
                        endDate.toString(),
                        "Valid Title",
                        "Valid description",
                        null);
        var validator = new ValidateOffer();

        assertThrows(
                InvalidOfferException.class,
                () -> {
                    validator.validate(offerView);
                });
    }

    @Test
    void testImageMaxLengthExceeded() throws MalformedURLException {
        var imageLink =
                new URL(
                        "https://example.com/long-image-link-that-exceeds-the-maximum-length-long-image-link-that-exceeds-the-maximum-length-long-image-link-that-exceeds-the-maximum-length-long-image-link-that-exceeds-the-maximum-length-long-image-link-that-exceeds-the-maximum-length.jpg");
        var id = UUID.randomUUID();
        var startDate = LocalDate.parse("2023-01-01");
        var endDate = LocalDate.parse("2023-02-01");
        OfferView offerView =
                new OfferView(
                        id,
                        startDate.toString(),
                        endDate.toString(),
                        "Valid Title",
                        "Valid description",
                        imageLink);
        var validator = new ValidateOffer();

        assertThrows(
                InvalidOfferException.class,
                () -> {
                    validator.validate(offerView);
                });
    }

    @Test
    void testValidImage() throws MalformedURLException {
        var imageLink = new URL("https://example.com/image.jpg");
        var id = UUID.randomUUID();
        var startDate = LocalDate.parse("2023-01-01");
        var endDate = LocalDate.parse("2023-02-01");
        OfferView offerView =
                new OfferView(
                        id,
                        startDate.toString(),
                        endDate.toString(),
                        "Valid Title",
                        "Valid description",
                        imageLink);
        var validator = new ValidateOffer();

        assertDoesNotThrow(
                () -> {
                    validator.validate(offerView);
                });
    }
}
