package de.muenchen.kobit.backend.offer.service;

import de.muenchen.kobit.backend.admin.service.AdminService;
import de.muenchen.kobit.backend.offer.model.Offer;
import de.muenchen.kobit.backend.offer.repository.OfferRepository;
import de.muenchen.kobit.backend.offer.view.OfferView;
import de.muenchen.kobit.backend.validation.OfferValidator;
import de.muenchen.kobit.backend.validation.exception.OfferValidationException;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OfferCreationService {

    private final OfferRepository offerRepository;
    private final AdminService adminService;
    private final List<OfferValidator> validators;

    public OfferCreationService(
            OfferRepository offerRepository,
            AdminService adminService,
            List<OfferValidator> validators) {
        this.offerRepository = offerRepository;
        this.adminService = adminService;
        this.validators = validators;
    }

    @Transactional
    public OfferView createOffer(OfferView offerView) throws OfferValidationException {
        // Perform validation using the registered validators
        for (OfferValidator validator : validators) {
            validator.validate(offerView);
        }

        // Check for admin rights or perform any admin-related operations here

        Offer newOffer = createNewOffer(offerView);
        return new OfferView(
                newOffer.getId(),
                newOffer.getStartDate(),
                newOffer.getEndDate(),
                newOffer.getTitle(),
                newOffer.getDescription(),
                newOffer.getImageLink());
    }

    private Offer createNewOffer(OfferView offerView) {
        return offerRepository.save(offerView.toOffer());
    }
}
