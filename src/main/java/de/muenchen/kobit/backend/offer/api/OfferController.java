package de.muenchen.kobit.backend.offer.api;

import de.muenchen.kobit.backend.offer.service.OfferCreationService;
import de.muenchen.kobit.backend.offer.service.OfferDeletionService;
import de.muenchen.kobit.backend.offer.service.OfferManipulationService;
import de.muenchen.kobit.backend.offer.service.OfferService;
import de.muenchen.kobit.backend.offer.view.OfferListItem;
import de.muenchen.kobit.backend.offer.view.OfferView;
import de.muenchen.kobit.backend.validation.exception.OfferValidationException;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/offers")
public class OfferController {

    private final OfferService offerService;
    private final OfferManipulationService manipulationService;
    private final OfferCreationService creationService;
    private final OfferDeletionService deletionService;

    public OfferController(
            OfferService offerService,
            OfferManipulationService manipulationService,
            OfferCreationService creationService,
            OfferDeletionService deletionService) {
        this.offerService = offerService;
        this.manipulationService = manipulationService;
        this.creationService = creationService;
        this.deletionService = deletionService;
    }

    @GetMapping
    public List<OfferListItem> getOfferList() {
        return offerService.getOfferList();
    }

    @GetMapping("/{id}")
    public OfferView getOfferById(@PathVariable UUID id) {
        return offerService.findById(id);
    }

    @PostMapping
    public OfferView createOffer(@RequestBody OfferView offerView) throws OfferValidationException {
        return creationService.createOffer(offerView);
    }

    @PutMapping("/{id}")
    public OfferView updateOffer(@PathVariable UUID id, @RequestBody OfferView offerView) throws OfferValidationException {
        return manipulationService.updateOffer(offerView, id);
    }

    @DeleteMapping("/{id}")
    public void deleteOffer(@PathVariable UUID id) {
        deletionService.deleteOffer(id);
    }
}
