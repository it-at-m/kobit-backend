package de.muenchen.kobit.backend.validation.offer;

import de.muenchen.kobit.backend.offer.view.OfferView;
import de.muenchen.kobit.backend.validation.OfferValidator;
import de.muenchen.kobit.backend.validation.exception.OfferValidationException;
import de.muenchen.kobit.backend.validation.exception.offer.InvalidOfferException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import org.springframework.stereotype.Component;

@Component
public class ValidateOffer implements OfferValidator<OfferView> {

    private static final int TITLE_MIN_LENGTH = 5;
    private static final int DESCRIPTION_MIN_LENGTH = 10;
    private static final int TITLE_MAX_LENGTH = 100;
    private static final int DESCRIPTION_MAX_LENGTH = 2500;
    private static final int IMAGE_LINK_MAX_LENGTH = 250;
    private static final DateTimeFormatter DATE_FORMATTER =
            DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @Override
    public void validate(OfferView offerView) throws OfferValidationException {
        // Validate date range
        LocalDate startDate;
        LocalDate endDate;

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        if (offerView.getStartDate() != null && offerView.getEndDate() != null) {
            try {
                startDate = LocalDate.parse(offerView.getStartDate().toString(), formatter);
            } catch (Exception e) {
                throw new InvalidOfferException("Failed to parse start date.");
            }

            try {
                endDate = LocalDate.parse(offerView.getEndDate().toString(), formatter);
            } catch (Exception e) {
                throw new InvalidOfferException("Failed to parse end date.");
            }

            if (startDate.isAfter(endDate)) {
                throw new InvalidOfferException("Start date cannot be greater than end date.");
            }

            if (endDate.isBefore(startDate)) {
                throw new InvalidOfferException("End date cannot be less than start date.");
            }
        }

        // Validate title
        String title = offerView.getTitle();
        if (title == null) {
            throw new InvalidOfferException("Title cannot be null.");
        }
        if (title.length() < TITLE_MIN_LENGTH) {
            throw new InvalidOfferException(
                    "Title must be at least " + TITLE_MIN_LENGTH + " characters long.");
        }
        if (title.length() > TITLE_MAX_LENGTH) {
            throw new InvalidOfferException(
                    "Title cannot exceed " + TITLE_MAX_LENGTH + " characters.");
        }

        // Validate description
        String description = offerView.getDescription();
        if (description == null) {
            throw new InvalidOfferException("Description cannot be null.");
        }
        if (description.length() < DESCRIPTION_MIN_LENGTH) {
            throw new InvalidOfferException(
                    "Description must be at least " + DESCRIPTION_MIN_LENGTH + " characters long.");
        }
        if (description.length() > DESCRIPTION_MAX_LENGTH) {
            throw new InvalidOfferException(
                    "Description cannot exceed " + DESCRIPTION_MAX_LENGTH + " characters.");
        }

        if (offerView.getImageLink() == null) {
            throw new InvalidOfferException("Image link cannot be null.");
        }
        if (offerView.getImageLink().toString().length() > IMAGE_LINK_MAX_LENGTH) {
            throw new InvalidOfferException(
                    "Image link cannot exceed " + IMAGE_LINK_MAX_LENGTH + " characters.");
        }
    }
}
