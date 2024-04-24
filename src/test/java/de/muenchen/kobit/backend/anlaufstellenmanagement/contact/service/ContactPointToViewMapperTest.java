package de.muenchen.kobit.backend.anlaufstellenmanagement.contact.service;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.clearAllCaches;

import de.muenchen.kobit.backend.competence.Competence;
import de.muenchen.kobit.backend.competence.model.CompetenceToContactPoint;
import de.muenchen.kobit.backend.competence.repository.CompetenceRepository;
import de.muenchen.kobit.backend.contact.model.Contact;
import de.muenchen.kobit.backend.contact.service.ContactPointToViewMapper;
import de.muenchen.kobit.backend.contact.service.ContactService;
import de.muenchen.kobit.backend.contactpoint.model.ContactPoint;
import de.muenchen.kobit.backend.contactpoint.view.ContactPointView;
import de.muenchen.kobit.backend.links.service.LinkService;
import de.muenchen.kobit.backend.links.view.LinkView;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class ContactPointToViewMapperTest {
    private final CompetenceRepository competenceRepository =
            Mockito.mock(CompetenceRepository.class);
    private final ContactService contactService = Mockito.mock(ContactService.class);
    private final LinkService linkService = Mockito.mock(LinkService.class);

    private ContactPointToViewMapper mapper;

    @BeforeEach
    void init() {
        clearAllCaches();

        // Mocking the necessary dependencies for ContactService
        ContactRepository contactRepository = Mockito.mock(ContactRepository.class);
        contactService =
                new ContactService(
                        contactRepository); // Assuming ContactService accepts ContactRepository in
        // its constructor

        // Mock the necessary method to return a list of contacts
        List<Contact> mockContacts = List.of(new Contact(UUID.randomUUID(), "test@example.com"));
        Mockito.when(contactRepository.findAllByContactPointId(Mockito.any(UUID.class)))
                .thenReturn(mockContacts);

        // Initialize other mocks
        competenceRepository = Mockito.mock(CompetenceRepository.class);
        linkService = Mockito.mock(LinkService.class);
        mapper = new ContactPointToViewMapper(competenceRepository, contactService, linkService);
    }

    @Test
    void contactPointToView_ObjectTest() throws MalformedURLException {
        URL imageUrl = new URL("https://text.com/image.jpg");
        UUID contactPointId = UUID.randomUUID();
        ContactPoint contactPoint =
                new ContactPoint(
                        contactPointId, "test", "test", "Beschreibung", List.of("test"), imageUrl);

        List<LinkView> links = List.of(new LinkView(contactPointId, "test", "test", false));
        List<CompetenceToContactPoint> competenceToContactPoint =
                List.of(
                        new CompetenceToContactPoint(contactPointId, Competence.DISCRIMINATION),
                        new CompetenceToContactPoint(contactPointId, Competence.EMPLOYEE));
        Mockito.when(linkService.getLinkViewsByContactPointId(contactPointId)).thenReturn(links);
        Mockito.when(competenceRepository.findAllByContactPointId(contactPointId))
                .thenReturn(competenceToContactPoint);

        ContactPointView result = mapper.contactPointToView(contactPoint);

        assertThat(result.getLinks().stream().findFirst().get().getUrl())
                .isEqualTo(links.stream().findFirst().get().getUrl());
        assertThat(result.getCompetences().size()).isEqualTo(2);
        assertThat(result.getDescription()).isEqualTo(contactPoint.getDescription());
    }
}
