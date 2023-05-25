package de.muenchen.kobit.backend.offer.service;

import de.muenchen.kobit.backend.offer.repository.OfferRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class OfferDeletionService {

    private final OfferRepository offerRepository;

    public OfferDeletionService(OfferRepository offerRepository) {
        this.offerRepository = offerRepository;
    }

    @Transactional
    public void deleteOffer(UUID id) {

        offerRepository.deleteById(id);

    }
}
