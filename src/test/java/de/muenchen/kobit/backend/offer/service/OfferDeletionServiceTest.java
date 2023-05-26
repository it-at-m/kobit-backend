package de.muenchen.kobit.backend.offer.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import de.muenchen.kobit.backend.offer.repository.OfferRepository;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class OfferDeletionServiceTest {

    private final OfferRepository offerRepository = mock(OfferRepository.class);

    private OfferDeletionService offerDeletionService;

    @BeforeEach
    void init() {
        offerDeletionService = new OfferDeletionService(offerRepository);
    }

    @Test
    void deleteOfferTest() {
        var id = UUID.randomUUID();

        doNothing().when(offerRepository).deleteById(any());

        offerDeletionService.deleteOffer(id);

        verify(offerRepository).deleteById(id);
    }
}
