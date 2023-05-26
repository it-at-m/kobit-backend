package de.muenchen.kobit.backend.offer.service;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import de.muenchen.kobit.backend.admin.model.AdminUserView;
import de.muenchen.kobit.backend.admin.service.AdminService;
import de.muenchen.kobit.backend.offer.repository.OfferRepository;
import de.muenchen.kobit.backend.offer.view.OfferView;
import de.muenchen.kobit.backend.validation.OfferValidator;
import de.muenchen.kobit.backend.validation.exception.OfferValidationException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class OfferManipulationServiceTest {

    private final OfferRepository offerRepository = mock(OfferRepository.class);
    private final AdminService adminService = mock(AdminService.class);
    private final List<OfferValidator> validators = List.of(mock(OfferValidator.class));

    private OfferManipulationService offerManipulationService;

    @BeforeEach
    void init() {
        offerManipulationService =
                new OfferManipulationService(offerRepository, adminService, validators);
    }

    @Test
    void updateOfferTest() throws OfferValidationException, MalformedURLException {
        var id = UUID.randomUUID();
        var imageLink = new URL("https://example.com/image.jpg");
        var view =
                new OfferView(
                        id,
                        "Test title",
                        "Test description",
                        "2023-01-01",
                        "2023-12-31",
                        imageLink);

        var offer = view.toOffer();
        offer.setId(id);

        when(offerRepository.findById(any())).thenReturn(Optional.of(offer));
        when(offerRepository.save(any())).thenReturn(offer);
        when(adminService.getAdminUserInfo()).thenReturn(new AdminUserView(true, false, "ITM"));

        var result = offerManipulationService.updateOffer(view, id);

        verify(offerRepository).save(any());

        assertThat(result).isInstanceOf(OfferView.class);
        assertThat(result.getTitle()).isEqualTo(view.getTitle());
        assertThat(result.getDescription()).isEqualTo(view.getDescription());
        assertThat(result.getStartDate()).isEqualTo(view.getStartDate());
        assertThat(result.getEndDate()).isEqualTo(view.getEndDate());
        assertThat(result.getImageLink()).isEqualTo(view.getImageLink());
    }
}
