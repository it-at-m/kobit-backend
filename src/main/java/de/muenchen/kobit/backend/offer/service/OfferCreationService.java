package de.muenchen.kobit.backend.offer.service;

import de.muenchen.kobit.backend.offer.model.Offer;
import de.muenchen.kobit.backend.offer.repository.OfferRepository;
import de.muenchen.kobit.backend.offer.view.OfferView;
import de.muenchen.kobit.backend.validation.OfferValidator;
import de.muenchen.kobit.backend.validation.exception.OfferValidationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class OfferCreationService {

    private final OfferRepository offerRepository;
    private final List<OfferValidator> validators;

    public OfferCreationService(OfferRepository offerRepository, List<OfferValidator> validators) {
        this.offerRepository = offerRepository;
        this.validators = validators;
    }

    @Transactional
    public OfferView createOffer(OfferView offerView) throws OfferValidationException {
        // Perform validation using the registered validators
        for (OfferValidator validator : validators) {
            validator.validate(offerView);
        }

        Offer newOffer = createNewOffer(offerView);
        return new OfferView(
                newOffer.getId(),
                newOffer.getStartDate(),
                newOffer.getEndDate(),
                newOffer.getTitle(),
                newOffer.getDescription(),
                newOffer.getImageLink()
        );
    }

    private Offer createNewOffer(OfferView offerView) {
        return offerRepository.save(offerView.toOffer());
    }
}
