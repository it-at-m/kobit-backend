package de.muenchen.kobit.backend.offer.api;

import de.muenchen.kobit.backend.offer.service.OfferService;
import de.muenchen.kobit.backend.offer.view.OfferListItem;
import de.muenchen.kobit.backend.offer.view.OfferView;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/offers")
public class OfferController {

    private final OfferService offerService;

    public OfferController(OfferService offerService) {
        this.offerService = offerService;
    }

    @GetMapping
    public List<OfferListItem> getOfferList() {
        return offerService.getOfferList();
    }

    @GetMapping("/{id}")
    public OfferView getOfferById(@PathVariable UUID id) {
        return offerService.findById(id);
    }
}
