package de.muenchen.kobit.backend.offer.service;

import de.muenchen.kobit.backend.admin.service.AdminService;
import de.muenchen.kobit.backend.offer.model.Offer;
import de.muenchen.kobit.backend.offer.repository.OfferRepository;
import de.muenchen.kobit.backend.offer.view.OfferView;
import de.muenchen.kobit.backend.validation.OfferValidator;
import de.muenchen.kobit.backend.validation.exception.OfferValidationException;
import de.muenchen.kobit.backend.validation.exception.offer.InvalidOfferException;
import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OfferManipulationService {

    private final OfferRepository offerRepository;
    private final AdminService adminService;
    private final List<OfferValidator> validators;

    public OfferManipulationService(
            OfferRepository offerRepository,
            AdminService adminService,
            List<OfferValidator> validators) {
        this.offerRepository = offerRepository;
        this.adminService = adminService;
        this.validators = validators;
    }

    @Transactional
    public OfferView updateOffer(OfferView offerView, UUID id) throws OfferValidationException {

        // Perform validation using the registered validators
        for (OfferValidator validator : validators) {
            validator.validate(offerView);
        }

        // Check for admin rights or perform any admin-related operations here

        // Validate the ID
        validateId(offerView.getId(), id);

        // Create or update the Offer
        Offer updatedOffer = createOrUpdateOffer(offerView, id);

        // Return the OfferView representation of the updated Offer
        return convertToOfferView(updatedOffer);
    }

    private void validateId(UUID offerId, UUID pathId) throws OfferValidationException {
        if (!offerId.equals(pathId)) {
            throw new InvalidOfferException("PathId and Id in the OfferView were not identical!");
        }
    }

    private Offer createOrUpdateOffer(OfferView offerView, UUID id) {
        Offer existingOffer = offerRepository.findById(id).orElse(new Offer());

        // Update the properties of the existing Offer with the values from the OfferView
        existingOffer.setStartDate(offerView.getStartDate());
        existingOffer.setEndDate(offerView.getEndDate());
        existingOffer.setTitle(offerView.getTitle());
        existingOffer.setDescription(offerView.getDescription());
        existingOffer.setImageLink(offerView.getImageLink());

        // Save the updated Offer
        return offerRepository.save(existingOffer);
    }

    private OfferView convertToOfferView(Offer offer) {
        // Convert the Offer object to an OfferView object
        return new OfferView(
                offer.getId(),
                offer.getStartDate(),
                offer.getEndDate(),
                offer.getTitle(),
                offer.getDescription(),
                offer.getImageLink());
    }
}
