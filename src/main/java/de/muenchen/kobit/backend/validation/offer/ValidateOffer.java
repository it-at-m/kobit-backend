package de.muenchen.kobit.backend.validation.offer;

import de.muenchen.kobit.backend.offer.view.OfferView;
import de.muenchen.kobit.backend.validation.OfferValidator;
import de.muenchen.kobit.backend.validation.exception.OfferValidationException;
import de.muenchen.kobit.backend.validation.exception.offer.InvalidOfferException;
import org.springframework.stereotype.Component;

@Component
public class ValidateOffer implements OfferValidator<OfferView> {

    private static final int TITLE_MAX_LENGTH = 250;
    private static final int DESCRIPTION_MAX_LENGTH = 2000;
    private static final int IMAGE_LINK_MAX_LENGTH = 250;

    @Override
    public void validate(OfferView offerView) throws OfferValidationException {
        if (offerView.getStartDate() == null) {
            throw new InvalidOfferException("Start date cannot be null.");
        }
        if (offerView.getEndDate() == null) {
            throw new InvalidOfferException("End date cannot be null.");
        }
        if (offerView.getTitle() == null) {
            throw new InvalidOfferException("Title cannot be null.");
        }
        if (offerView.getTitle().length() > TITLE_MAX_LENGTH) {
            throw new InvalidOfferException(
                    "Title cannot exceed " + TITLE_MAX_LENGTH + " characters.");
        }
        if (offerView.getDescription() == null) {
            throw new InvalidOfferException("Description cannot be null.");
        }
        if (offerView.getDescription().length() > DESCRIPTION_MAX_LENGTH) {
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
