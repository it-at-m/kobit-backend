package de.muenchen.kobit.backend.contactpoint.service;

import static org.mockito.Mockito.*;

import de.muenchen.kobit.backend.competence.service.CompetenceService;
import de.muenchen.kobit.backend.contact.service.ContactService;
import de.muenchen.kobit.backend.contactpoint.repository.ContactPointRepository;
import de.muenchen.kobit.backend.links.service.LinkService;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ContactPointDeletionServiceTest {

    private final ContactPointRepository contactPointRepository =
            mock(ContactPointRepository.class);
    private final ContactService contactService = mock(ContactService.class);
    private final LinkService linkService = mock(LinkService.class);
    private final CompetenceService competenceService = mock(CompetenceService.class);

    private ContactPointDeletionService deletionService;

    @BeforeEach
    void init() {
        // clearAllCaches();
        deletionService =
                new ContactPointDeletionService(
                        contactPointRepository, contactService, linkService, competenceService);
    }

    @Test
    void deleteContactPointTest() {
        var id = UUID.randomUUID();
        deletionService.deleteContactPointView(id);
        verify(competenceService).deleteCompetencesByContactPointId(id);
        verify(linkService).deleteLinkByContactPointId(id);
        verify(contactService).deleteContactsByContactPointId(id);
    }
}
